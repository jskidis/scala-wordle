package com.skidis.wordle
package strategy

trait ClusterStrategy extends SolveStrategy with WordHintsGenerator with WordPatternMatcher  {
  case class WordClusterCount(word: XordlePhrase, clusterCount: Int)

  override def reduceWordSet(wordSet: WordSet, currentGuess: String, wordHints: WordHints): WordSet = {
    // Create list of tuple with each letter of the current word and the hint for that letter
    val wordPattern: List[(Char, HintBlock)] = currentGuess.toList zip wordHints

    // Eliminate words from set that don't fit word hints for current answer
    wordSet.filter { w: XordlePhrase => doesWordMatch(w.phrase, wordPattern) && w.phrase != currentGuess }
  }

  override def generateNextGuess(remainingWords: WordSet, hintProps: HintProps): (String, String) = {
    // Next Guess is based on word with most unique clusters, with ties resolved based on type
    val sortedClustersByWord = generateClusters(remainingWords, hintProps).sortWith(sortWordCluster)
    val nextGuess = sortedClustersByWord.head
    (nextGuess.word.phrase, s"Most Unique Clusters: ${nextGuess.clusterCount}")
  }

  def generateClusters(remainingWords: WordSet, hintProps: HintProps): List[WordClusterCount] = {
    // Generate a set for each remaining word identifying the number unique clusters choosing that word would created
    remainingWords.toList.map { potentialAnswer: XordlePhrase =>
      val clusterCount = remainingWords.map { word: XordlePhrase => generateWordHints(potentialAnswer, word, hintProps) }.size
      WordClusterCount tupled(potentialAnswer, clusterCount)
    }
  }

  def sortWordCluster(wc1: WordClusterCount, wc2: WordClusterCount): Boolean = {
    if(wc1.clusterCount != wc2.clusterCount) wc1.clusterCount > wc2.clusterCount else wc1.word > wc2.word
  }
}

object ClusterStrategy extends ClusterStrategy
