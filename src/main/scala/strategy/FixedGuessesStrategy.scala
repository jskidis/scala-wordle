package com.skidis.wordle
package strategy

trait FixedGuessesStrategy extends SolveStrategy {
  def fixedGuesses: Seq[String]

  abstract override def generateNextGuesses(remainingWords: WordSet, previousGuesses: Seq[(String, WordHints)], numToReturn: Int)
  : Seq[String] = {
    if(fixedGuesses.size <= previousGuesses.size) super.generateNextGuesses(remainingWords, previousGuesses, numToReturn)
    else {
      val fixedWord = fixedGuesses(previousGuesses.size)
      if (remainingWords.exists{ w: XordlePhrase => w.phrase == fixedWord }) Vector(fixedWord)
      else super.generateNextGuesses(remainingWords, previousGuesses, numToReturn)
    }
  }
}
