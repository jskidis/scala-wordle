package com.skidis.wordle

import BlockColor.BlockColor

object ClusterWithFrequencyStrategy extends SolveStrategy {
  def reduceWordSet(
    wordSet: Set[_ <: WordleWord],
    currentGuess: String,
    colorPattern: List[BlockColor])
  : Set[_ <: WordleWord] = {

    // Create list of tuple with each letter of the current word and the color for that letter
    val wordPattern: List[(Char, BlockColor)] = currentGuess.toList zip colorPattern

    // Eliminate words from set that don't fit color pattern for current answer
    wordSet.filter { w =>
      WordPatternMatcher.doesWordMatch(w.wordString(), wordPattern) && w.wordString() != currentGuess
    }
  }

  def generateNextGuess(remainingWords: Set[_ <: WordleWord]): (String, String) = {
    // Generate a set for each remaining word identifying the number unique clusters choosing that word would created
    val wordClusters: List[WordClusterCount] = remainingWords.toList.map { word =>
      WordClusterCount tupled (word, remainingWords.map{ w =>
        WordColorPatternGenerator.generate(word, w)
      }.size)
    }

    def sortWordCluster(wc1: WordClusterCount, wc2: WordClusterCount): Boolean = {
      if(wc1.clusterCount != wc2.clusterCount) wc1.clusterCount > wc2.clusterCount
      else wc1.word > wc2.word
    }

    // Next Guess is based on word with most unique clusters, with ties resolved based on type
    val sortedClustersByWord = wordClusters.sortWith(sortWordCluster)
    val nextGuess = sortedClustersByWord.head

    (nextGuess.word.wordString(), s"Most Unique Clusters: ${nextGuess.clusterCount}")
  }
}