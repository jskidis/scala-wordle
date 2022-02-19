package com.skidis.wordle

import BlockColor.BlockColor

import scala.collection.mutable

trait ClusterStrategy extends SolveStrategy {
  case class WordClusterCount(word: WordleWord, clusterCount: Int)

  def reduceWordSet(wordSet: WordSet, currentGuess: String, colorPattern: List[BlockColor]): WordSet = {
    // Create list of tuple with each letter of the current word and the color for that letter
    val wordPattern: List[(Char, BlockColor)] = currentGuess.toList zip colorPattern

    // Eliminate words from set that don't fit color pattern for current answer
    wordSet.filter { w: WordleWord =>
      WordPatternMatcher.doesWordMatch(w.wordString(), wordPattern) && w.wordString() != currentGuess
    }
  }

  def generateNextGuess(remainingWords: WordSet): (String, String) = {
    // Generate a set for each remaining word identifying the number unique clusters choosing that word would created
    val wordClusters: List[WordClusterCount] = remainingWords.toList.map { potentialAnswer: WordleWord =>
      val clusterCount = remainingWords.map{ word : WordleWord => generateWordPattern(potentialAnswer, word)}.size
      WordClusterCount tupled (potentialAnswer, clusterCount)
    }

    // Next Guess is based on word with most unique clusters, with ties resolved based on type
    val sortedClustersByWord = wordClusters.sortWith(sortWordCluster)
    val nextGuess = sortedClustersByWord.head

    (nextGuess.word.wordString(), s"Most Unique Clusters: ${nextGuess.clusterCount}")
  }

  def generateWordPattern(answer: WordleWord, word: WordleWord): ColorPattern = {
    WordColorPatternGenerator.generate(answer, word)
  }

  def sortWordCluster(wc1: WordClusterCount, wc2: WordClusterCount): Boolean = (wc1.word, wc2.word) match {
    case (wwf1:WordleWordFrequencies, wwf2:WordleWordFrequencies) =>
      wc1.clusterCount + Math.log10(wwf1.frequency)*2 > wc2.clusterCount + Math.log10(wwf2.frequency)*2
    case (ww1: WordleWord, ww2: WordleWord) =>
      if(wc1.clusterCount != wc2.clusterCount) wc1.clusterCount > wc2.clusterCount else ww1 > ww2
  }
}

object ClusterStrategy extends ClusterStrategy {
  val patternCache: mutable.Map[(WordleWord, WordleWord), ColorPattern] =
    collection.mutable.Map[(WordleWord, WordleWord), ColorPattern]()

  override def generateWordPattern(answer: WordleWord, word: WordleWord): ColorPattern = {
    patternCache.getOrElseUpdate((answer, word), super.generateWordPattern(answer, word))
  }
}

trait ReverseClusterStrategy extends ClusterStrategy {
  override def sortWordCluster(wc1: WordClusterCount, wc2: WordClusterCount): Boolean = !super.sortWordCluster(wc1, wc2)
}

object ReverseClusterStrategy extends ReverseClusterStrategy
