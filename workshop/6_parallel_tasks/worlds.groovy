hello = {
  exec "cp $input $output"
}

world = {
  exec "cp $input $output"
}

mars = {
  exec "cp $input $output"
}

venus = {
  exec "cp $input $output"
}

blue = {
  exec "cp $input $output"
}

red = {
  exec "cp $input $output"
}

nice_to_see_you = {
  exec "cp $input $output"
}


run { hello + world }

// run { hello + [world,mars] }

// run { hello + [ blue + world, red + mars ]  }

// run { hello + [ blue + world, red + mars ] + nice_to_see_you }

// run { hello + [ blue + world, red + [mars,venus] ] + nice_to_see_you }
