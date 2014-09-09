// THIS IS A CLOSURE
testing_files =
{
  def f = new File("File.txt")
  assert f.name == 'File.txt'
  assert ! f.isAbsolute()
  assert f.path == 'File.txt'
  assert f.parent == null

  println f.absolutePath  // return a String
  println f.absoluteFile  // return a File
  println f.canonicalPath // return a canonicalPath
  println f.toURI()       // return a URI

  println "PARENT: ${f.absoluteFile.parent}"

  println "ITERATE IN FILE CONTENT:"
  f.eachLine { line ->
    println line
  }

  // all file as a text (multiline string)
  println "${'-'.center(20,'-')}"
  print f.getText().replaceAll("a","X")
}

try
{
  // RUN WITH BPIPE IF WE HAVE Bpipe in CLASSPATH
  Bpipe.run { testing_files }
}
catch(Exception e)
{
  // RUN WITH GROOVY: execute the closure
  testing_files()
}
