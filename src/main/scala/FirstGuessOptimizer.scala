package com.skidis.wordle

trait FirstGuessOptimizer extends SolveStrategy with GuessAndAnswerSets with HintProps with ConsoleWriter {
  def generateTopGuess(numToReturn: Int): Seq[String] = {
    val startTimestamp = System.currentTimeMillis()

    val phrases = generateNextGuesses(guessSet, Nil, numToReturn)
    phrases.foreach(writeLine)

    val endTimestamp = System.currentTimeMillis()
    writeLine(s"Time Elapsed: ${(endTimestamp - startTimestamp) / 1000}")

    phrases
  }
}

trait FirstGuessRunner extends FirstGuessOptFactory {
  def runOptimizer(numGuesses: Int): Unit = {
    val optimizer = createFirstGuessOptimizer()
    optimizer.generateTopGuess(numGuesses)
  }
}