package com.skidis.wordle

trait ReverseClusterStrategy extends ClusterStrategy {
  override def sortWordCluster(wc1: WordClusterCount, wc2: WordClusterCount): Boolean = {
    if(wc1.clusterCount != wc2.clusterCount) wc1.clusterCount < wc2.clusterCount else wc1.word < wc2.word
  }

  override def generateNextGuess(remainingWords: WordSet): (String, String) = {
    val (nextGuess, info) = super.generateNextGuess(remainingWords)
    (nextGuess, info.replace("Most", "Fewest"))
  }
}

object ReverseClusterStrategy extends ReverseClusterStrategy