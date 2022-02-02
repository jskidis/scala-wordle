package com.skidis.wordle

import BlockColor.BlockColor

import scala.annotation.tailrec

object WordleProcessor {
  @tailrec
  def process(colorPatternGenerator: ColorPatternGenerator, debugOutput: Boolean = true)
             (wordSet: Set[String], currentGuess: String = "TRACE", guessNumber: Int = 1)
  : Option[(String, Int)] = {

    if (debugOutput) println(s"Current Guess: $currentGuess, Guess #:$guessNumber")

    // Generate Color Pattern Based On Input
    val colorPattern = colorPatternGenerator(currentGuess)
    if (debugOutput) println(s"Color Pattern: ${colorPattern.mkString(", ")}")

    if (colorPattern.isEmpty) None // User entered an empty string, so abort without finishing
    else if (colorPattern == winningColorPattern) Option(currentGuess, guessNumber) // color pattern is all green, so word has been guessed
    else {
      // Create list of tuple with each letter of the current word and the color for that letter
      val wordPattern: List[(Char, BlockColor)] = currentGuess.toList zip colorPattern

      // Eliminate words from set that don't fit color pattern for current answer
      val remainingWords = wordSet.filter(w => WordPatternMatcher.doesWordMatch(w, wordPattern) && w != currentGuess)

      // Generate a set for each remaining word identifying the number unique clusters choosing that word would created
      val clustersByWord: List[WordClusterCount] = remainingWords.toList.map { word =>
        WordClusterCount tupled (word, remainingWords.map(w => WordColorPatternGenerator.generate(word, w)).size)
      }

      // Next Guess is based on word with most unique clusters
      val sortedClustersByWord = clustersByWord.sortWith(_.clusterCount > _.clusterCount)
      val nextGuess = sortedClustersByWord.head

      if (debugOutput) {
        println(s"Remaining Words: ${remainingWords.size}")
        println(s"Most Unique Clusters: ${nextGuess.clusterCount}")
        println()
      }

      // Start over with next guess
      process(colorPatternGenerator, debugOutput)(remainingWords, nextGuess.word, guessNumber +1)
    }
  }
}
