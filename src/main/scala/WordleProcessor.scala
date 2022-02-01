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

    if (colorPattern.isEmpty) None // User entered an empty string, so abort without finishing
    else if (colorPattern == winningColorPattern) Option(currentGuess, guessNumber) // color pattern is all green, so word has been guessed
    else {
      if (debugOutput) println(s"Color Pattern: ${colorPattern.mkString(", ")}")

      // Create list of tuple with each letter of the current word and the color for that letter
      val wordPattern: List[(Char, BlockColor)] = currentGuess.toList zip colorPattern

      // Eliminate words from set that don't fit color pattern for current answer
      val remainingWords = wordSet.filter(w => w != currentGuess && WordPatternGenerator.generateFromWord(w, wordPattern))

      // Generate a set with a pair of each word and the number of unique clusters from remaining word set for that word
      val wordsNumClusters: Set[(String, Int)] = remainingWords.map { word =>
        (word, WordClusterGenerator.generateUnique(word, remainingWords))
      }

      // Next Guess is based on word with most unique clusters
      val wordWithMostClusters = wordsNumClusters.reduceLeft { (left, right) =>
        if (left._2 >= right._2) left else right
      }
      val nextGuess = wordWithMostClusters._1

      if (debugOutput) {
        println(s"Remaining Words: ${remainingWords.size}")
        println(s"Most Unique Clusters: ${wordWithMostClusters._2}")
        println()
      }

      // Start over with next guess
      process(colorPatternGenerator, debugOutput)(remainingWords, nextGuess, guessNumber +1)
    }
  }
}