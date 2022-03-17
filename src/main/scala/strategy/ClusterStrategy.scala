package com.skidis.wordle
package strategy

trait ClusterStrategy extends WordScoringStrategy with HardModeWordElimStrategy with WordHintsGenerator  {

  override def scoreWordFunction(remainingWords: WordSet): XordlePhrase => Double = {
    scoreWord(remainingWords)
  }

  def scoreWord(remainingWords: WordSet)(potentialAnswer: XordlePhrase): Double = {
    remainingWords.map { word: XordlePhrase => generateWordHints(potentialAnswer, word) }.size
  }
}

trait ClusterStrategyCaching extends ClusterStrategy with CachingWordHintsGenerator
