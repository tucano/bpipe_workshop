import groovy.text.SimpleTemplateEngine

template_test = {
  def engine = new SimpleTemplateEngine()
  def template = new File("template.html").text

  def binding = [
    "TITLE"  : "A SIMPLE TEMPLATE ENGINE EXAMPLE",
    "HELLO"  : "Bungiorno",
    "INJECT" : """<img src="http://i.imgur.com/wJ6qV.jpg" title="Hosted by imgur.com" />""",
    "TABLE"  : "<tr><td>ONE</td><td>TWO</td></tr><tr><td>A</td><td>B</td></tr>"
  ]

  def output = engine.createTemplate(template).make(binding)
  new File("output.html").write(output.toString())
}

try
{
  // RUN WITH BPIPE IF WE HAVE Bpipe in CLASSPATH
  Bpipe.run { template_test }
}
catch(groovy.lang.MissingPropertyException e)
{
  template_test()
}
