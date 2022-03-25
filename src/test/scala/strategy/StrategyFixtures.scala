package com.skidis.wordle
package strategy

trait SolveStrategyWithReduceWordSetFixture extends SolveStrategy {
  override def reduceWordSet(wordSet: WordSet,
    currentGuess: String, wordHints: WordHints)
  : WordSet = wordSet
}

trait SolveStrategyWithNextGuessFixture {
  def generateNextGuesses(remainingWords: WordSet,
    previousGuesses: Seq[(String, WordHints)], numToReturn: Int)
  : Seq[String] = remainingWords.take(numToReturn).map(_.text).toSeq
}
