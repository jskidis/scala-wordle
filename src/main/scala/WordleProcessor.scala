package com.skidis.wordle

import BlockColor.Green

import scala.annotation.tailrec

object WordleProcessor {
  val winningColorPattern = List(Green, Green, Green, Green, Green)

  @tailrec
  def process(
    solver: SolveStrategy,
    guessGatherer: GuessGatherer,
    colorPatternGenerator: ColorPatternGenerator,
    lineWriter: LineWriterF = Console.println)
  ( wordSet: WordSet,
    suggestion: String,
    guesses: List[(String, ColorPattern)] = Nil)
  : List[(String, ColorPattern)] = {

    lineWriter(s"\n${List.fill(40)('*').mkString}")

    val (currentGuess, colorPattern) =
      if (wordSet.size == 1) (suggestion, winningColorPattern)
      else {
        val guess = guessGatherer(suggestion)
        val pattern = colorPatternGenerator(guess)
        (guess, pattern)
      }

    lineWriter(s"Current Guess: $currentGuess, Guess #:${guesses.size +1}")

    if (wordSet.size == 1) lineWriter("Only 1 choice left!!!")
    lineWriter(s"${colorPattern.mkString}")

    val updatedGuesses = guesses :+ (currentGuess, colorPattern)

    if (colorPattern.isEmpty) Nil // User entered an empty string, so abort without finishing
    else if (colorPattern == winningColorPattern) updatedGuesses // color pattern is all green, so word has been guessed
    else if (updatedGuesses.size == 6) updatedGuesses :+ ("", Nil)
    else {
      // Eliminate words from set based on current guess and color pattern
      val remainingWords = solver.reduceWordSet(wordSet, currentGuess, colorPattern)
      lineWriter(s"Remaining Words: ${remainingWords.size}")

      // Determine next guess and start next iteration
      val (nextGuess, info) = solver.generateNextGuess(remainingWords)
      lineWriter(info)
      process(solver, guessGatherer, colorPatternGenerator, lineWriter)(remainingWords, nextGuess, updatedGuesses)
    }
  }
}
