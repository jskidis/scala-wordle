package com.skidis.wordle
package strategy

trait ClusterStrategy extends StandardWordElimStrategy with CachingWordHintsGenerator  {
  case class WordClusterCount(word: XordlePhrase, clusterCount: Int)

  override def generateNextGuesses(remainingWords: WordSet, number: Int): Seq[XordlePhrase] = {
    // Generate a set for each remaining word identifying the number unique clusters choosing that word would created
    val clusters = remainingWords.toVector.map { potentialAnswer: XordlePhrase =>
      WordClusterCount(potentialAnswer,
        remainingWords.map { word: XordlePhrase => generateWordHints(potentialAnswer, word) }.size)
    }

    // Next Guess is based on the words with most unique clusters, with ties resolved based on type
    val sortedClusters = clusters.sortWith(sortWordCluster)
    sortedClusters.take(number).map(_.word)
  }

  def sortWordCluster(wc1: WordClusterCount, wc2: WordClusterCount): Boolean = {
    if(wc1.clusterCount != wc2.clusterCount) wc1.clusterCount > wc2.clusterCount else wc1.word > wc2.word
  }
}
