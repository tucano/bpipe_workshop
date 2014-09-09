gatk_call_variants =
{
  // We don;t have GATL in PATH: we just build the command
  def command = """
    java -Xmx5g -jar GenomeAnalysisTK.jar -T UnifiedGenotyper
      -R hg19.fa
      -D dbsnp_132.hg19.vcf
      -glm BOTH
      -I sample.bam
      -stand_call_conf 5
      -stand_emit_conf 5
      -o sample.vcf
      -metrics sample.metrics
      -L $chr
  """

  println "Command:\n$command"
}

run { chr(1..22,'X','Y') * [ gatk_call_variants ] }
