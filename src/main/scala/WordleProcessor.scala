package com.skidis.wordle

import BlockColor.Green

import scala.annotation.tailrec

trait WordleProcessor extends SolveStrategy with GuessRetriever with ColorPatternRetriever with Writer {
  val winningColorPattern = List(Green, Green, Green, Green, Green)

  def process(wordSet: WordSet, suggestion: String): List[(String, ColorPattern)] = {
    processRecurse(wordSet, suggestion)
  }

  @tailrec
  private def processRecurse(wordSet: WordSet, suggestion: String, guesses: List[(String, ColorPattern)] = Nil)
  : List[(String, ColorPattern)] = {

    writeLine(s"${List.fill(40)('*').mkString}")

    val (currentGuess, colorPattern) =
      if (wordSet.size == 1) (suggestion, winningColorPattern)
      else {
        val guess = retrieveGuess(suggestion)
        val pattern = retrieveColorPattern(guess)
        (guess, pattern)
      }

    writeLine(s"Current Guess: $currentGuess, Guess #:${guesses.size +1}")

    if (wordSet.size == 1) writeLine("Only 1 choice left!!!")
    writeLine(s"${colorPattern.mkString}")

    val updatedGuesses = guesses :+ (currentGuess, colorPattern)

    if (colorPattern.isEmpty) Nil // User entered an empty string, so abort without finishing
    else if (colorPattern == winningColorPattern) updatedGuesses // color pattern is all green, so word has been guessed
    else if (updatedGuesses.size == 6) updatedGuesses :+ ("", Nil)
    else {
      // Eliminate words from set based on current guess and color pattern
      val remainingWords = reduceWordSet(wordSet, currentGuess, colorPattern)
      writeLine(s"Remaining Words: ${remainingWords.size}")

      // Determine next guess and start next iteration
      val (nextGuess, info) = generateNextGuess(remainingWords)
      writeLine(info)
      processRecurse(remainingWords, nextGuess, updatedGuesses)
    }
  }
}
