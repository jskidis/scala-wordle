package com.skidis.wordle

import BlockColor.BlockColor

import scala.annotation.tailrec

object WordleProcessor {
  @tailrec
  def process(colorPatternGenerator: ColorPatternGenerator, debugOutput: Boolean = true)
    (wordSet: Set[_ <: WordleWord],
      currentGuess: String = "TRACE",
      guesses: List[(String, List[BlockColor])] = Nil
    )
  : List[(String, List[BlockColor])] = {

    if (debugOutput) {
      println()
      println(List.fill(40)('*').mkString)
      println(s"Current Guess: $currentGuess, Guess #:${guesses.size +1}")
    }

    // Generate Color Pattern Based On Input or Answer or whatever function turns a guess into a color pattern
    val colorPattern = colorPatternGenerator(currentGuess)
    if (debugOutput) {
      println(colorPattern.mkString)
      println()
    }

    val updatedGuesses = guesses :+ (currentGuess, colorPattern)

    if (colorPattern.isEmpty) Nil // User entered an empty string, so abort without finishing
    else if (colorPattern == winningColorPattern) updatedGuesses // color pattern is all green, so word has been guessed
    else {
      // Create list of tuple with each letter of the current word and the color for that letter
      val wordPattern: List[(Char, BlockColor)] = currentGuess.toList zip colorPattern

      // Eliminate words from set that don't fit color pattern for current answer
      val remainingWords = wordSet.filter(w => WordPatternMatcher.doesWordMatch(
        w.wordString(), wordPattern) && w.wordString() != currentGuess)

      // Generate a set for each remaining word identifying the number unique clusters choosing that word would created
      val wordClusters: List[WordClusterCount] = remainingWords.toList.map { word =>
        WordClusterCount tupled (word, remainingWords.map{ w =>
          WordColorPatternGenerator.generate(word, w)
        }.size)
      }

      val nextGuess = determineNextGuess(wordClusters)
      if (debugOutput) {
        println(s"Remaining Words: ${remainingWords.size}")
        println(s"Most Unique Clusters: ${nextGuess.clusterCount}")
      }

      // Start over with next guess
      process(colorPatternGenerator, debugOutput)(remainingWords, nextGuess.word.wordString(), updatedGuesses)
    }
  }

  private def determineNextGuess(wordClusters: List[WordClusterCount]): WordClusterCount = {
    def sortWordCluster(wc1: WordClusterCount, wc2: WordClusterCount): Boolean = {
      if(wc1.clusterCount != wc2.clusterCount) wc1.clusterCount > wc2.clusterCount
      else wc1.word > wc2.word
    }
    // Next Guess is based on word with most unique clusters, with ties resolved based on type
    val sortedClustersByWord = wordClusters.sortWith(sortWordCluster)
    sortedClustersByWord.head

  }
}
