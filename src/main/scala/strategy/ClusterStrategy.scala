package com.skidis.wordle
package strategy

trait ClusterStrategy extends StandardWordElimStrategy with WordHintsGenerator  {
  case class WordClusterCount(word: XordlePhrase, clusterCount: Int)

  override def generateNextGuesses(remainingWords: WordSet, previousGuesses: Seq[String], numToReturn: Int): Seq[String] = {
    // Generate a set for each remaining word identifying the number unique clusters choosing that word would created
    val clusters = remainingWords.map { potentialAnswer: XordlePhrase =>
      WordClusterCount(potentialAnswer,
        remainingWords.map { word: XordlePhrase => generateWordHints(potentialAnswer, word) }.size)
    }

    // Next Guess is based on the words with most unique clusters, with ties resolved based on type
    val sortedClusters = clusters.toVector.sortWith(sortWordCluster)
    sortedClusters.take(numToReturn).map(_.word.phrase)
  }

  def sortWordCluster(wc1: WordClusterCount, wc2: WordClusterCount): Boolean = {
    if(wc1.clusterCount != wc2.clusterCount) wc1.clusterCount > wc2.clusterCount else wc1.word > wc2.word
  }
}

trait ClusterStrategyCaching extends ClusterStrategy with CachingWordHintsGenerator
