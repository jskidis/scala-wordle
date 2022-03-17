package com.skidis.wordle
package strategy

trait ClusterStrategy extends WordScoringStrategy with HardModeWordElimStrategy with WordHintsGenerator  {

  override def scoreWordFunction(remainingWords: WordSet): XrdleWord => Double = {
    scoreWord(remainingWords)
  }

  def scoreWord(remainingWords: WordSet)(potentialAnswer: XrdleWord): Double = {
    remainingWords.map { word: XrdleWord => generateWordHints(potentialAnswer, word) }.size
  }
}

trait ClusterStrategyCaching extends ClusterStrategy with CachingWordHintsGenerator
