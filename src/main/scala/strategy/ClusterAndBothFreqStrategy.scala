package com.skidis.wordle
package strategy

import hintgen.CachingWordHintsGenerator

trait ClusterAndBothFreqStrategy extends WordScoringStrategy with HardModeWordElimStrategy {

  override def scoreWordFunction(remainingWords: WordSet): XrdleWord => Double = {
    val clusterScoreFunc = clusterStrategy.scoreWordFunction(remainingWords)
    val wordFreqScoreFunc = wordFreqStrategy.scoreWordFunction(remainingWords)
    val charFreqScoreFunc = charFreqStrategy.scoreWordFunction(remainingWords)
    (word: XrdleWord) => clusterScoreFunc(word) +
      wordFreqScoreFunc(word) *2 + charFreqScoreFunc(word) / 8
  }

  def clusterStrategy: ClusterStrategy = ClusterStrategy
  def wordFreqStrategy: WordFreqStrategy = WordFreqStrategy
  def charFreqStrategy: CharFreqStrategy = CharFreqStrategy
}

object ClusterAndBothFreqStrategy extends ClusterAndBothFreqStrategy

trait ClusterAndBothFreqStrategyCaching extends ClusterAndBothFreqStrategy {
  override def clusterStrategy: ClusterStrategy = new ClusterStrategy with CachingWordHintsGenerator
}

