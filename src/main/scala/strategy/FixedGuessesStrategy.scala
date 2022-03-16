package com.skidis.wordle
package strategy

trait FixedGuessesStrategy extends SolveStrategy {
  def fixedGuesses: Seq[String]

  abstract override def generateNextGuesses(remainingWords: WordSet, previousGuesses: Seq[(String, WordHints)], numToReturn: Int)
  : Seq[String] = {
    if(fixedGuesses.size > previousGuesses.size) Vector(fixedGuesses(previousGuesses.size))
    else super.generateNextGuesses(remainingWords, previousGuesses, numToReturn)
  }
}
