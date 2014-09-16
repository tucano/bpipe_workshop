/**************************************************************************
 * EXEC
 * Bpipe automatically removes newlines from commands
 * so that you do not need to worry about it.
 **************************************************************************/

// This is a global "pipeline" variable, scope: the whole pipeline
GENOME="../../minify/genome/chr22.fa"

mpileup =
{
  // In next sessions we will see $input and $output special variables
  exec """
    samtools mpileup
    -u
    -f $GENOME
    $input > $output
  """
  // we can add ", false" to exec we avoid newlines removal
}

run { mpileup }
