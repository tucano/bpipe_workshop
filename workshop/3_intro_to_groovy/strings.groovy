// THIS IS A CLOSURE
testing_strings =
{
  /*
   * This is a multiline comment
   */

  // THIS IS A SINGLE LINE COMMENT

  // Groovy uses both " and ' for strings. Either can be used.
  // Using either type of string allows you to use strings with quotations easily.
  println "he said 'cheese' once"
  println 'he said "cheese!" again'

  // Strings may be concatenated with "+".
  String world = "world"
  print "hello " + world + "\n"

  // Regular strings in Groovy can span multiple lines, you use the """ syntax.
  println """
    This is an indented multiline String
    This is an indented multiline String
    This is an indented multiline String
  """

  // We can strip indentation from multiline strings:
  println """
    This is an indented multiline String
    This is an indented multiline String
    This is an indented multiline String
  """.stripIndent()

  /*
   * Strings that are declared inside double-quotes
   * (i.e. either single double-quotes or triple double-quotes for multi-line strings)
   * can contain arbitrary expressions inside them as shown above using the ${expression}
   *
   * "def" is a replacement for a type name.
   * In variable definitions it is used to indicate that you don't care about the type.
   *
   */
  def name = "James"
  def text = """\
  hello there ${name}
  how are you today?
  """
  println(text)

  String foxtype = 'quick'
  foxcolor = ['b', 'r', 'o', 'w', 'n']
  println "The $foxtype ${foxcolor.join()} fox"

  // Some string methods
  def s = 'abcdefg'
  def t = "If winter comes can spring be far behind"
  def u = "If winter comes, can spring be far behind?"
  println """
    SIZE:       ${s.size()}
    SUBSTRING:  ${s.substring(2,5)}
    PADDING:
    Pad a String to a minimum length specified by numberOfChars
    by adding the space character to the right/left as many times as needed.

    ${s.padRight(14,'+')}
    ${s.padLeft(14,'+')}
    ${s.padLeft(9,'-').padRight(11,'-')}
    ${s.center((s.size() * 2) -1, '+')}

    TOKENIZE: split strings into tokens (tokens for split are '\\s\\t\\n\\r\\f')
    ${t.tokenize()}
    You can supply our own tokens.
    PHRASE: $u
    USING TOKENS: '\\s',',','?'
    TOKENIZED: ${u.tokenize(" ,?")}
  """.stripIndent()

  // STRINGS ARE IMMUTABLE IN GROOVY
  String x = "If winter comes, can spring be far behind?"
  println "THE STRING IS:"
  println x
  5.times { i ->
    println "Trying to add something (tentative number: $i) to the string"
    x << "adding: $i"
  }
  println "THE STRING WILL NOT CHANGE:"
  println x

  // A StringBuffer is a mutable string.
  def sb1= new StringBuffer()
  println "default initial capacity is: ${sb1.capacity()}"
  5.times { i ->
    // We add a random string of N elements
    String rand_s = (1..10).inject("") { a, b -> a += ('a'..'z')[new Random().nextFloat() * 26 as int] }
    sb1 << "$rand_s" << "\n"
  }
  println sb1
}

try
{
  // RUN WITH BPIPE IF WE HAVE Bpipe in CLASSPATH
  Bpipe.run { testing_strings }
}
catch(groovy.lang.MissingPropertyException e)
{
  // RUN WITH GROOVY: execute the closure
  testing_strings()
}
