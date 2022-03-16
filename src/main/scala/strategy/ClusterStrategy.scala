package com.skidis.wordle
package strategy

trait ClusterStrategy extends StandardWordElimStrategy with WordHintsGenerator with WordScoring {

  override def generateNextGuesses(remainingWords: WordSet, previousGuesses: Seq[(String, WordHints)], numToReturn: Int)
  : Seq[String] = scoreAndSortWords(scoreWord)(remainingWords).take(numToReturn)

  def scoreWord(potentialAnswer: XordlePhrase, remainingWords: WordSet): Double = {
    remainingWords.map { word: XordlePhrase => generateWordHints(potentialAnswer, word) }.size
  }
}

trait ClusterStrategyCaching extends ClusterStrategy with CachingWordHintsGenerator
