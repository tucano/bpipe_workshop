about title: "Bpipe var and using"

hello = {
  var greetings : "Hello"

  doc "Run hello with $greetings"

  exec "echo $greetings"
}

world = {
  var name : "Earth"

  doc "Run world with $name"

  exec "echo $name"
}

run { hello.using(greetings: "Bom dia") + world.using(name: "Mundo") }
