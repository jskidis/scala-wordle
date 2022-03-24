package com.skidis.wordle
package strategy

trait FixedGuessesStrategy extends SolveStrategy {
  def fixedGuesses: Seq[String]

  abstract override def generateNextGuesses(remainingWords: WordSet, previousGuesses: Seq[(String, WordHints)], numToReturn: Int)
  : Seq[String] = {
    if(fixedGuesses.size <= previousGuesses.size) super.generateNextGuesses(remainingWords, previousGuesses, numToReturn)
    else {
      val fixedWord = fixedGuesses(previousGuesses.size)
      if (useGivenGuess(fixedWord, remainingWords)) Vector(fixedWord)
      else super.generateNextGuesses(remainingWords, previousGuesses, numToReturn)
    }
  }

  def useGivenGuess(fixedWord: String, remainingWords: WordSet ): Boolean = true
}

trait FixedGuessHardModeStrategy extends FixedGuessesStrategy {
  override def useGivenGuess(fixedWord: String, remainingWords: WordSet): Boolean = {
    remainingWords.exists { w: XrdleWord => w.text == fixedWord }
  }
}

trait FixedGuessWithThresholdStrategy extends FixedGuessesStrategy {
  // The number of remaining words required in order to use a fixed guess that is not in the remaining word set
  def numRemainingWordsThreshold: Int

  override def useGivenGuess(fixedWord: String, remainingWords: WordSet): Boolean = {
    remainingWords.size >= numRemainingWordsThreshold ||
      remainingWords.exists { w: XrdleWord => w.text == fixedWord }
  }
}
