package com.skidis.wordle
package strategy

import hintgen.CachingWordHintsGenerator

trait ClusterAndFreqStrategy extends WordScoringStrategy with HardModeWordElimStrategy {

  override def scoreWordFunction(remainingWords: WordSet): XrdleWord => Double = {
    val clusterScoreFunc = clusterStrategy.scoreWordFunction(remainingWords)
    val wordFreqScoreFunc = wordFreqStrategy.scoreWordFunction(remainingWords)
    (word: XrdleWord) => clusterScoreFunc(word) + wordFreqScoreFunc(word) *2
  }

  def clusterStrategy: ClusterStrategy = ClusterStrategy
  def wordFreqStrategy: WordFreqStrategy = WordFreqStrategy
}

object ClusterAndFreqStrategy extends ClusterAndFreqStrategy

trait ClusterAndFreqStrategyCaching extends ClusterAndFreqStrategy {
  override def clusterStrategy: ClusterStrategy = new ClusterStrategy with CachingWordHintsGenerator
}
