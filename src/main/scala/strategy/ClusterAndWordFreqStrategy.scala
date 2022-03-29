package com.skidis.wordle
package strategy

trait ClusterAndWordFreqStrategy extends WordScoringStrategy with HardModeWordElimStrategy {

  override def scoreWordFunction(remainingWords: WordSet): XrdleWord => Double = {
    val clusterScoreFunc = clusterStrategy.scoreWordFunction(remainingWords)
    val wordFreqScoreFunc = wordFreqStrategy.scoreWordFunction(remainingWords)
    (word: XrdleWord) => clusterScoreFunc(word) + wordFreqScoreFunc(word) *2
  }

  lazy val clusterStrategy: ClusterStrategy = new ClusterStrategy with GenericHintProps
  lazy val wordFreqStrategy: WordFreqStrategy = WordFreqStrategy
}

object ClusterAndWordFreqStrategy extends ClusterAndWordFreqStrategy
