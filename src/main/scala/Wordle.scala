package com.skidis.wordle

import BlockColor.{BlockColor, Green}

import scala.annotation.tailrec
import scala.io.{Source, StdIn}

object Wordle extends App {
  val winningColorPattern = List(Green, Green, Green, Green, Green)
  val candidateWords = WordReader(Source.fromResource("words.txt"))
  val initialGuess = "TRACE"

  val result = process(initialGuess, candidateWords)

  println(result match {
    case None => "Process Aborted By User"
    case Some(word) => s"Correct Answer: $word"
  })

  @tailrec
  def process(currentGuess: String, wordSet: Set[String], guessNumber: Int = 1): Option[String] = {
    println(s"Current Guess: $currentGuess, Guess #:$guessNumber")

    // Generate Color Pattern Based On Input
    val colorPattern = GatherResult(StdIn.readLine, Console.println, InputValidator.apply)

    if (colorPattern.isEmpty) None // User entered an empty string, so abort without finishing
    else if (colorPattern == winningColorPattern) Option(currentGuess) // color pattern is all green, so word has been guessed
    else {
      println(s"Color Pattern: ${colorPattern.mkString(", ")}")

      // Create list of tuple with each letter of the current word and the color for that letter
      val wordPattern: List[(Char, BlockColor)] = currentGuess.toList zip colorPattern

      // Eliminate words from set that don't fit color pattern for current answer
      val remainingWords = wordSet.filter(w => w != currentGuess && WordMatchesWordPattern(w, wordPattern))

      // Generate a set with a pair of each word and the number of unique clusters from remaining word set for that word
      val wordsNumClusters: Set[(String, Int)] = remainingWords.map { word =>
        (word, GenerateWordClusters(word, remainingWords).size)
      }

      // Next Guess is based on word with most unique clusters
      val wordWithMostClusters = wordsNumClusters.reduceLeft { (left, right) => if (left._2 >= right._2) left else right }
      val nextGuess = wordWithMostClusters._1

      // Start over with next guess
      process(nextGuess, remainingWords, guessNumber +1)
    }
  }
}
