package com.skidis.wordle

import BlockColor.BlockColor

object ClusterWithFrequencyStrategy extends SolveStrategy {
  case class WordClusterCount(word: WordleWord, clusterCount: Int)

  def reduceWordSet(wordSet: WordSet, currentGuess: String, colorPattern: List[BlockColor])
  : WordSet = {

    // Create list of tuple with each letter of the current word and the color for that letter
    val wordPattern: List[(Char, BlockColor)] = currentGuess.toList zip colorPattern

    // Eliminate words from set that don't fit color pattern for current answer
    wordSet.filter { w: WordleWord =>
      WordPatternMatcher.doesWordMatch(w.wordString(), wordPattern) && w.wordString() != currentGuess
    }
  }

  def generateNextGuess(remainingWords: WordSet): (String, String) = {
    // Generate a set for each remaining word identifying the number unique clusters choosing that word would created
    val wordClusters: List[WordClusterCount] = remainingWords.toList.map { word: WordleWord =>
      val clusterCount = remainingWords.map{ w: WordleWord => WordColorPatternGenerator.generate(word, w)}.size
      WordClusterCount tupled (word, clusterCount)
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