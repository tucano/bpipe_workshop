about title: "Bpipe multi"

hello = {
  produce("mars.txt","jupiter.txt","earth.txt") {
    multi "echo mars > $output1.txt",
        "echo jupiter > $output2.txt",
        "echo earth > $output3.txt"
  }
}

run { hello }
