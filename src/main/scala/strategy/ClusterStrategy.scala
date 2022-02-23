package com.skidis.wordle
package strategy

import BlockColor.BlockColor

trait ClusterStrategy extends SolveStrategy with WordColorPatternGenerator with WordPatternMatcher {
  case class WordClusterCount(word: XordlePhrase, clusterCount: Int)

  def reduceWordSet(wordSet: WordSet, currentGuess: String, colorPattern: List[BlockColor]): WordSet = {
    // Create list of tuple with each letter of the current word and the color for that letter
    val wordPattern: List[(Char, BlockColor)] = currentGuess.toList zip colorPattern

    // Eliminate words from set that don't fit color pattern for current answer
    wordSet.filter { w: XordlePhrase => doesWordMatch(w.phrase, wordPattern) && w.phrase != currentGuess }
  }

  def generateNextGuess(remainingWords: WordSet): (String, String) = {
    // Generate a set for each remaining word identifying the number unique clusters choosing that word would created
    val wordClusters: List[WordClusterCount] = remainingWords.toList.map { potentialAnswer: XordlePhrase =>
      val clusterCount = remainingWords.map{ word : XordlePhrase => generateWordColorPattern(potentialAnswer, word)}.size
      WordClusterCount tupled (potentialAnswer, clusterCount)
    }

    // Next Guess is based on word with most unique clusters, with ties resolved based on type
    val sortedClustersByWord = wordClusters.sortWith(sortWordCluster)
    val nextGuess = sortedClustersByWord.head

    (nextGuess.word.phrase, s"Most Unique Clusters: ${nextGuess.clusterCount}")
  }


  def sortWordCluster(wc1: WordClusterCount, wc2: WordClusterCount): Boolean = {
    if(wc1.clusterCount != wc2.clusterCount) wc1.clusterCount > wc2.clusterCount else wc1.word > wc2.word
  }
}

object ClusterStrategy extends ClusterStrategy
