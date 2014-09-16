about title: "Bpipe var and using: recycle stages"

hello = {
  var time : 2.0

  exec "sleep $time"
  exec """echo "hello $name" """
}

run {
  hello.using(time: 10, name: "world") + hello.using(time:5, name:"mars")
}
