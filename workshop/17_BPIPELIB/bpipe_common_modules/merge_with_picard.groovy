merge_with_picard =
{
  var validation_stringency : "SILENT"
  var create_index          : false
  var msd                   : true
  var assumed_sorted        : true
  var use_threading         : true

  doc title: "Merge bam files",
      desc: """
      <p>
        Use <b>MergeSamFiles</b> to merge bam files. Path of PICARD: $PICMERGE <br/>
        Merged with this options:<br/>
        <table>
          <tr><td>VALIDATION_STRINGENCY</td><td>$validation_stringency</td></tr>
          <tr><td>CREATE_INDEX</td><td>$create_index</td></tr>
          <tr><td>MSD</td><td>$msd</td></tr>
          <tr><td>ASSUME_SORTED</td><td>$assumed_sorted</td></tr>
          <tr><td>USE_THREADING</td><td>$use_threading</td></tr>
        </table>
      </p>
      """,
      author: "davide.rambaldi@gmail.com"

  def outputs = [ input.prefix.replaceAll(/_[0-9]{3}/,"") + '.bam' ]
  produce(outputs)
  {
    // PICARD need inputs as I=input1.bam I=input2.bam then:
    def input_string = inputs.collect() { bam -> "I=" + bam }.join(" ")
    exec """
      $PICMERGE $input_string
        O=$output
        VALIDATION_STRINGENCY=$validation_stringency
        CREATE_INDEX=$create_index
        MSD=$msd
        ASSUME_SORTED=$assumed_sorted
        USE_THREADING=$use_threading
    """
  }
}
