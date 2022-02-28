package com.skidis.wordle

trait FirstGuessOptimizator extends SolveStrategy with GuessAndAnswerSets with HintProps with ConsoleWriter {
  def generateTopGuess(number: Int): Seq[XordlePhrase] = {
    val startTimestamp = System.currentTimeMillis()

    val phrases = generateNextGuesses(guessSet, number)
    phrases.foreach { p => writeLine(p.phrase) }

    val endTimestamp = System.currentTimeMillis()
    writeLine(s"Time Elapsed: ${(endTimestamp - startTimestamp) / 1000}")

    phrases
  }

  /*
  override def generateClusters(remainingWords: WordSet): Vector[WordClusterCount] = {
    val batches = remainingWords.grouped(2500)
    val batchFutures = batches.map { batch => Future(super.generateClusters(batch)) }
    Await.result(Future.sequence(batchFutures), 1.hour).flatten.toVector
  }
*/
}

trait FirstGuessRunner extends XordleRunner {
  def runOptimizer(numGuesses: Int): Unit = {
    val optimizater = createFirstGuessOptimizer()
    val guesses = optimizater.generateTopGuess(numGuesses)
    guesses.foreach(println)
  }
}