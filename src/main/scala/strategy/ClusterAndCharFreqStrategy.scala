package com.skidis.wordle
package strategy

trait ClusterAndCharFreqStrategy extends WordScoringStrategy with HardModeWordElimStrategy {

  override def scoreWordFunction(remainingWords: WordSet): XrdleWord => Double = {
    val clusterScoreFunc = clusterStrategy.scoreWordFunction(remainingWords)
    val charFreqScoreFunc = charFreqStrategy.scoreWordFunction(remainingWords)
    (word: XrdleWord) => clusterScoreFunc(word) + charFreqScoreFunc(word) / 10
  }

  lazy val clusterStrategy: ClusterStrategy = new ClusterStrategy with GenericHintProps
  lazy val charFreqStrategy: CharFreqStrategy = CharFreqStrategy
}
