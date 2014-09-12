#!/usr/bin/env groovy

// USAGE: explicit_branch_map.groovy raw_data/sample*/*.fgz
import static groovy.json.JsonOutput.*

def extensions = ['fastq','fastq.gz','fqz','fgz']
def branches = [:]

args.each { a ->
  def input_file = new File(a)

  // IS FILE?
  if ( !input_file.isFile() )
  {
    println "INPUT: $input_file is not a file."
    System.exit(1)
  }

  def file_name = input_file.canonicalPath
  def check_extension = extensions.collect() { e -> file_name.endsWith(e) }

  // Iterates over the elements of a collection, and checks whether at least one element is true according to the Groovy Truth.
  if (!check_extension.any())
  {
    println "Input file: ${input_file.absolutePath} don't have a known extension, known extensions are: ${extensions.join(', ')}"
    System.exit(1)
  }

  // MAP KEY IS THE SAMPLE DIR
  def sample_dir = input_file.getParentFile().getName()

  if ( !branches.containsKey(sample_dir) )
  {
    // ADD SAMPLESHEET TO LIST AT FIRST ITERATION ONLY and check if exists
    def samplesheet = input_file.getParentFile().canonicalPath + '/SampleSheet.csv'

    if ( new File(samplesheet).exists() )
    {
      branches[sample_dir] = [samplesheet]
    }
    else
    {
      println "Can't find SampleSheet.csv file"
    }
    branches[sample_dir].push file_name
  }
  else
  {
    branches[sample_dir].push file_name
  }

}


println prettyPrint(toJson(branches))
