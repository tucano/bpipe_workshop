#!/bin/bash
# simple script to compute alll bwa alignment in a dir.
run=false
test "x$1" = "x--run" && run=true

# from original makefile 

BWA=/usr/local/cluster/bin/bwa
SAMTOOLS=/usr/local/cluster/bin/samtools
BWAOPT_ALN="-q 30"
BWAOPT_PE=
BWAOPT_SE=
PICMERGE=/usr/local/cluster/bin/MergeSamFiles.jar
MARKDUP=/usr/local/cluster/bin/MarkDuplicates.jar
PICOPTS=
VALIDATION_STRINGENCY=SILENT
CREATE_INDEX=true
MSD=true
ASSUME_SORTED=true

#
# to easily change where to write temporary files scratch: 
#  default: present directory..


# to replace with PBS_WKDIR
cd $PWD

fcid=`tail -1 SampleSheet.csv | cut -d',' -f1 `
reference=`tail -1 SampleSheet.csv | cut -d',' -f4`
name=`tail -1 SampleSheet.csv | cut -d',' -f3`
rindex=`tail -1 SampleSheet.csv | cut -d',' -f5`
[[ "$rindex" == "" ]] && rindex=NoIndex
ref_genome=/lustre1/genomes/$reference/bwa/$reference

echo "$fcid"
echo "$reference"

JOBLIST=
BAMFILES=

experiment_name=${fcid}_${name}
user=`whoami`
LOCAL_SCRATCH=/lustre2/scratch/${RANDOM}_${user}/${experiment_name}
mkdir -p $LOCAL_SCRATCH

echo $experiment_name
#------------------------------------------------------------------------#

for file in *$name*R1*.fastq.gz
do
	# foreach combination of file write and submit the job

	# identify rindex lane and index

	echo $file
	lane=`echo $file | rev | cut -d'_' -f 3 | rev`
	index=`echo $file  | rev| cut -d'_' -f 1 | rev`
	indx=` echo $index| cut -d'.' -f 1 `
#	echo $lane, $index ,$indx

	# this is the name:

	R1=$name"_"$rindex"_"$lane"_R1_"$indx".fastq.gz"
	R2=$name"_"$rindex"_"$lane"_R2_"$indx".fastq.gz"
	R2_final=$experiment_name"_"$rindex"_"$lane"_R2_"$indx
	R1_final=$experiment_name"_"$rindex"_"$lane"_R1_"$indx
	R_final=$experiment_name"_"$rindex"_"$lane"_"$indx

#        job_nameR1="gz_"${R1_final:(-11)} 
#        job_nameR2="gz_"${R2_final:(-11)} 
#        job_nameR="sampe_"${R_final:(-8)} 
        job_nameR1="a_${R1_final}"
        job_nameR2="a_"${R2_final} 
        job_nameR="s_"${R_final} 
	#------------------------------------------------------------------------#

	# write the first job script
	SCRIPT=job.$R1_final
	cat <<__EOF__> $SCRIPT
#PBS -l select=1:ncpus=2:mem=16g:app=java
#PBS -N ${job_nameR1:0:15}
#PBS -M cittaro.davide@hsr.it
#PBS -P ${experiment_name}
#PBS -m a

cd $PWD

/usr/bin/time $BWA aln -t 2 $BWAOPT_ALN $ref_genome $R1 >$LOCAL_SCRATCH/tmp.$R1_final
__EOF__

	if $run
	then
		job_id1="$(qsub job.$R1_final)"
		test "x$job_id1" = "x" && { echo >&2 "*** error while submitting job $SCRIPT" ; exit 1 ; }
		qstat  $job_id1 || { echo >&2 "*** couldn't check for job $job_id1 (R1)" ; exit 1 ; }
	fi

	#------------------------------------------------------------------------#

	# write the second job script

	SCRIPT=job.$R2_final
	cat <<__EOF__> $SCRIPT
#PBS -l select=1:ncpus=2:mem=16g:app=java
#PBS -N ${job_nameR2:0:15}
#PBS -M cittaro.davide@hsr.it
#PBS -P ${experiment_name}
#PBS -m a

cd $PWD

/usr/bin/time $BWA aln -t 2 $BWAOPT_ALN $ref_genome $R2 >$LOCAL_SCRATCH/tmp.$R2_final
__EOF__

	if $run
	then
		job_id2="$(qsub job.$R2_final)"
		test "x$job_id2" = "x" && { echo >&2 "*** error while submitting job $SCRIPT" ; exit 1 ; }
		qstat   $job_id2 || { echo >&2 "*** couldn't check for job $job_id2 (R2)" ; exit 1 ; }
	fi

	#------------------------------------------------------------------------#

	# create the third job: runs only if previous two successful.

	SCRIPT=job.final.$R_final
	cat <<__EOF__> $SCRIPT
#PBS -l select=1:ncpus=2:mem=24g:app=java
#PBS -W depend=afterok:$job_id1:$job_id2
#PBS -N ${job_nameR:0:15}
#PBS -M cittaro.davide@hsr.it
#PBS -P ${experiment_name}
#PBS -m a

TMP_SCRATCH=/dev/shm/\${RANDOM}_${user}/${experiment_name}
mkdir -p \$TMP_SCRATCH
cd $PWD

/usr/bin/time $BWA sampe $BWAOPT_PE -r "@RG\tID:$experiment_name"_"$lane\tPL:illumina\tPU:$fcid\tLB:$experiment_name\tSM:$name\tCN:CTGB" $ref_genome $LOCAL_SCRATCH/tmp.$R1_final $LOCAL_SCRATCH/tmp.$R2_final  $R1 $R2 >\$TMP_SCRATCH/tmp.sampe.$R_final
cd \$TMP_SCRATCH
$SAMTOOLS view -Su \$TMP_SCRATCH/tmp.sampe.$R_final | $SAMTOOLS sort - $R_final
mv ${R_final}.bam ${LOCAL_SCRATCH}
rm -fr \`dirname \${TMP_SCRATCH}\`
__EOF__


	if $run
	then
		id_final="$(qsub $SCRIPT)"
		test "x$id_final" = "x" && { echo >&2 "*** error while submitting $SCRIPT" ; exit 1 ; }
		qstat   $id_final || { echo >&2 "*** couldn't check for job $id_final (final)" ; exit 1 ; }
	fi

	test "x$id_final" != "x" && JOBLIST=$JOBLIST:$id_final
        BAMFILES=$BAMFILES" "I=${LOCAL_SCRATCH}/$R_final".bam"

done

#------------------------------------------------------------------------#

# 


JOBLIST=${JOBLIST#:}
BAMFILES=${BAMFILES#" "}

job_nameC="combin"$experiment_name 
SCRIPT=job.combine.$R_final

cat <<__EOF__> $SCRIPT
#PBS -l select=1:ncpus=8:mem=48g:app=java
#PBS -W depend=afterok:$JOBLIST
#PBS -N ${job_nameC:0:15}
#PBS -M cittaro.davide@hsr.it
#PBS -P ${experiment_name}
#PBS -m a
#PBS -m e

cd $PWD

/usr/bin/time  java -jar $PICMERGE $BAMFILES \
        O=$experiment_name.bam \
        CREATE_INDEX=true \
        MSD=true \
        VALIDATION_STRINGENCY=SILENT \
        ASSUME_SORTED=true \
        USE_THREADING=true

java -Djava.io.tmpdir=/lustre2/scratch -Xmx24g -jar $MARKDUP \
        I=$experiment_name.bam \
        O=${experiment_name}_dedup.bam \
        CREATE_INDEX=true \
        VALIDATION_STRINGENCY=SILENT \
        REMOVE_DUPLICATES=false \
        METRICS_FILE=${experiment_name}_dedup.metrics

exit_status=\$?
/usr/local/cluster/bin/samtools flagstat ${experiment_name}_dedup.bam
/bin/rm -f $LOCAL_SCRATCH/tmp.$R1_final
/bin/rm -f $LOCAL_SCRATCH/tmp.$R2_final
/bin/rm -f $LOCAL_SCRATCH/tmp.sampe.$R_final

/bin/mv ${experiment_name}_dedup.bam $experiment_name.bam.lock
/bin/mv ${experiment_name}_dedup.bai $experiment_name.bai.lock

[[ \$exit_status -eq 0 ]] && /bin/rm -f *.bam *.bai

/bin/mv $experiment_name.bam.lock $experiment_name.bam 
/bin/mv $experiment_name.bai.lock $experiment_name.bai

[[ \$exit_status -eq 0 ]] && rm -fr $LOCAL_SCRATCH
__EOF__

if $run
then
	id_combine="$(qsub $SCRIPT)"
	test "x$id_combine" = "x" && { echo >&2 "*** error while submitting $SCRIPT" ; exit 1 ; }
	qstat  $id_combine || { echo >&2 "*** couldn't check for job $id_combine (combine)" ; exit 1 ; }
fi

#------------------------------------------------------------------------#

exit
