package com.skidis.wordle

trait FirstGuessOptimizer extends SolveStrategy with GuessAndAnswerSets with HintProps with ConsoleWriter {
  def generateTopGuess(number: Int): Seq[XordlePhrase] = {
    val startTimestamp = System.currentTimeMillis()

    val phrases = generateNextGuesses(guessSet, number)
    phrases.foreach { p => writeLine(p.phrase) }

    val endTimestamp = System.currentTimeMillis()
    writeLine(s"Time Elapsed: ${(endTimestamp - startTimestamp) / 1000}")

    phrases
  }
}

trait FirstGuessRunner extends XordleRunner {
  def runOptimizer(numGuesses: Int): Unit = {
    val optimizer = createFirstGuessOptimizer()
    optimizer.generateTopGuess(numGuesses)
  }
}