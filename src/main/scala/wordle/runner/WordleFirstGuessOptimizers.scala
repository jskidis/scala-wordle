package com.skidis.wordle
package wordle.runner

import runners.FirstGuessRunner

object FirstGuessSuggestion { val number = 12 }

object WordleFirstGuessOptStandardRunner extends App
  with FirstGuessRunner with WordleStandardRunner {
  runOptimizer(FirstGuessSuggestion.number)
}

object WordleFirstGuessOptAnswerOnlyRunner extends App
  with FirstGuessRunner with WordleAnswerOnlyRunner {
  runOptimizer(FirstGuessSuggestion.number)
}

object WordleFirstGuessOptCharFreqRunner extends App
  with FirstGuessRunner with WordleCharFreqRunner {
  runOptimizer(FirstGuessSuggestion.number)
}

object WordleFirstGuessOptWordFreqRunner extends App
  with FirstGuessRunner with WordleWordFreqRunner {
  runOptimizer(FirstGuessSuggestion.number)
}

object WordleFirstGuessOptReverseRunner extends App
  with FirstGuessRunner  with WordleReverseRunner {
  runOptimizer(FirstGuessSuggestion.number)
}
