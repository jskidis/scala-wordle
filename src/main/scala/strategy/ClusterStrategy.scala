package com.skidis.wordle
package strategy

trait ClusterStrategy extends WordScoringStrategy with HardModeWordElimStrategy with ClusterGenerator {

  override def scoreWordFunction(remainingWords: WordSet): XrdleWord => Double = {
    scoreWord(remainingWords)
  }

  def scoreWord(remainingWords: WordSet)(potentialAnswer: XrdleWord): Double = {
    generateUniquePatterns(potentialAnswer, remainingWords).size
  }
}

trait ClusterStrategyCaching extends ClusterStrategy with CachingWordHintsGenerator

trait ClusterGenerator extends WordHintsGenerator {
  def generateUniquePatterns(potentialAnswer: XrdleWord, wordSet: WordSet): Set[WordHints] = {
    wordSet.map { word: XrdleWord => generateWordHints(potentialAnswer, word) }
  }
}
