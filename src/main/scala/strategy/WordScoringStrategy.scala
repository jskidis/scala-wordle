package com.skidis.wordle
package strategy

case class WordScore(word: XrdleWord, score: Double)

trait WordScoringStrategy extends SolveStrategy {
  def scoreWordFunction(wordSet: WordSet): XrdleWord => Double

  override def generateNextGuesses(remainingWords: WordSet, previousGuesses: Seq[(String, WordHints)], numToReturn: Int)
  : Seq[String] = {
    val function = scoreWordFunction(remainingWords)
    val sortedWordScores = generateSortedWordScores(function, remainingWords)
    sortedWordScores.map(_.word.text).take(numToReturn)
  }

  def generateSortedWordScores(scoreWordFunction: XrdleWord => Double, remainingWords: WordSet)
  : Vector[WordScore] = {
    val wordScores = remainingWords.map { potentialAnswer: XrdleWord =>
      WordScore(potentialAnswer, scoreWordFunction(potentialAnswer))
    }
    sortWordScores(wordScores.toVector)
  }

  def sortWordScores(wordScores: Vector[WordScore]): Vector[WordScore] = wordScores.sortBy(-_.score)
}
