package com.skidis.wordle
package nerdle.runner

import runners.FirstGuessRunner

object FirstGuessSuggestion { val number = 12 }

trait NerdleFirstGuessOptStandardRunner extends FirstGuessRunner with NerdleStandardRunner
object NerdleFirstGuessOptStandardRunner extends App with NerdleFirstGuessOptStandardRunner {
  runOptimizer(FirstGuessSuggestion.number)
}

trait NerdleFirstGuessOptMiniRunner extends FirstGuessRunner with NerdleMiniRunner
object NerdleFirstGuessOptMiniRunner extends App with NerdleFirstGuessOptMiniRunner {
  runOptimizer(FirstGuessSuggestion.number)
}
