// THIS IS A CLOSURE
testing_map =
{
  /***********************************************************************************************
   * MAPS
   * Maps can be created using the following syntax. Notice that [:] is the empty map expression.
   * Map keys are strings by default: [a:1] is equivalent to ["a":1].
   * But if you really want a variable to become the key,
   * you have to wrap it between parentheses: [(a):1].
   ***********************************************************************************************/
  def map = [name:"Gromit", likes:"cheese", id:1234]
  assert map.get("name") == "Gromit"
  assert map.get("id") == 1234
  assert map["name"] == "Gromit"
  assert map['id'] == 1234
  assert map instanceof java.util.Map

  def emptyMap = [:]
  assert emptyMap.size() == 0
  emptyMap.put("foo", 5)
  assert emptyMap.size() == 1
  assert emptyMap.get("foo") == 5

  // Maps also act like beans so you can use the property notation
  // to get/set items inside the Map provided that the keys are
  // Strings which are valid Groovy identifiers.
  assert map.name == "Gromit"
  assert map.id == 1234
  emptyMap = [:]
  assert emptyMap.size() == 0
  emptyMap.foo = 5
  assert emptyMap.size() == 1
  assert emptyMap.foo == 5

  println "$map"
}

try
{
  // RUN WITH BPIPE IF WE HAVE Bpipe in CLASSPATH
  Bpipe.run { testing_map }
}
catch(Exception e)
{
  // RUN WITH GROOVY: execute the closure
  testing_map()
}
