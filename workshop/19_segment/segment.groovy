about title: "Segments"

hello = {
   exec "echo hello"
}

world = {
   exec "echo world"
}

hello_world = segment {
  hello + world
}

Bpipe.run {
  hello_world
}
