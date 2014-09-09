// Create a data structure (Map) that maps branches to files
def branches = [
    sample1: ["input_1_1.txt", "input_1_2.txt"],
    sample2: ["input_2_1.txt", "input_2_2.txt"],
    sample3: ["input_3_1.txt", "input_3_2.txt"]
]

align = {
  // keep the sample name as a branch variable
  branch.sample = branch.name
  println "BRANCH: ${branch.name} INPUTS: $inputs"
}

run { branches * [align] }
