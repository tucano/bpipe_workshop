// THIS IS A CLOSURE
testing_list =
{
  // LIST: You can create lists as follows. Notice that [] is the empty list expression.
  def list = [5, 6, 7, 8]
  assert list.get(2) == 7
  assert list[2] == 7
  assert list instanceof java.util.List
  def emptyList = []
  assert emptyList.size() == 0
  emptyList.add(5)
  assert emptyList.size() == 1

  // RANGES: Ranges allow you to create a list of sequential values.
  // an inclusive range
  def range = 5..8
  assert range.size() == 4
  assert range.get(2) == 7
  assert range[2] == 7
  assert range instanceof java.util.List
  assert range.contains(5)
  assert range.contains(8)

  // lets use a half-open range
  range = 5..<8
  assert range.size() == 3
  assert range.get(2) == 7
  assert range[2] == 7
  assert range instanceof java.util.List
  assert range.contains(5)
  assert ! range.contains(8)

  //get the end points of the range without using indexes
  range = 1..10
  assert range.from == 1
  assert range.to == 10

  // an inclusive range
  range = 'a'..'d'
  assert range.size() == 4
  assert range.get(2) == 'c'
  assert range[2] == 'c'
  assert range instanceof java.util.List
  assert range.contains('a')
  assert range.contains('d')
  assert ! range.contains('e')

  // Ranges can be used to iterate using the for statement.
  for (i in 1..10) {
    println "FOR: Hello ${i}"
  }

  // You can do the same with each
  (1..10).each { i ->
    println "EACH: Hello ${i}"
  }

  // Ranges can be also used in the switch statements:
  def years = 15
  def interestRate
  switch(years)
  {
    case 1..10: interestRate = 0.076; break;
    case 11..25: interestRate = 0.052; break;
    default: interestRate = 0.037;
  }

  /***********************************************************************************************
   * THE MAGIC START-DOT '*.' OPERATOR and The Safe Navigation Operator (?.)
   *
   * The Spread Operator is used to invoke an action on all items of an aggregate object.
   * The Safe Navigation operator is used to avoid a NullPointerException.
   * Typically when you have a reference to an object you might need to verify
   * that it is not null before accessing methods or properties of the object
   *
   * parent*.action
   *
   * equivalent to:
   *
   * parent.collect{ child -> child?.action }
   *
   ***********************************************************************************************/
  assert 3 == ['a', 'few', 'words'].size()
  assert [1,3,5] == ['a', 'few', 'words']*.size()
}

try
{
  // RUN WITH BPIPE IF WE HAVE Bpipe in CLASSPATH
  Bpipe.run { testing_list }
}
catch(groovy.lang.MissingPropertyException e)
{
  // RUN WITH GROOVY: execute the closure
  testing_list()
}
