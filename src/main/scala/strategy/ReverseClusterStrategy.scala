package com.skidis.wordle
package strategy

trait ReverseClusterStrategy extends ClusterStrategy {
  override def sortWordCluster(wc1: WordClusterCount, wc2: WordClusterCount): Boolean = {
    !super.sortWordCluster(wc1, wc2)
  }

  override def generateNextGuess(remainingWords: WordSet, hintProps: HintProps): (String, String) = {
    val (nextGuess, info) = super.generateNextGuess(remainingWords, hintProps)
    (nextGuess, info.replace("Most", "Fewest"))
  }
}

object ReverseClusterStrategy extends ReverseClusterStrategy