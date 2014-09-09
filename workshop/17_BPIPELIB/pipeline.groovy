about title: "A pipeline with common modules"

FQZ_COMP         = "~/src/fqzcomp-4.6/fqz_comp"
REFERENCE_GENOME = "../../minify/genome/chr22.fa"
PICMERGE         = "java -jar ~/libexec/picard/MergeSamFiles.jar"

run { "%_R*_%." * [align_with_bwa] + merge_with_picard }
