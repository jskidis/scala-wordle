package com.skidis.wordle
package strategy

case class WordScore(word: XordlePhrase, score: Double)

trait WordScoringStrategy extends SolveStrategy {
  def scoreWordFunction(wordSet: WordSet): XordlePhrase => Double

  override def generateNextGuesses(remainingWords: WordSet, previousGuesses: Seq[(String, WordHints)], numToReturn: Int)
  : Seq[String] = {
    val function = scoreWordFunction(remainingWords)
    val sortedWordScores = generateSortedWordScores(function, remainingWords)
    sortedWordScores.map(_.word.phrase).take(numToReturn)
  }

  def generateSortedWordScores(scoreWordFunction: XordlePhrase => Double, remainingWords: WordSet)
  : Vector[WordScore] = {
    val wordScores = remainingWords.map { potentialAnswer: XordlePhrase =>
      WordScore(potentialAnswer, scoreWordFunction(potentialAnswer))
    }
    wordScores.toVector.sortBy(-_.score)
  }
}
