println "number of assessments: ${assessments.size()}"

assessments.groupBy{ it.person.state }
           .collectEntries{ state,list-> 
               [state,list.size()] }
           
avgByState = assessments.groupBy{ it.person.state }
                        .collectEntries { state, list ->
                            [state, list.sum{it.score} / list.size()] }            
println "Average By State: $avgByState"           