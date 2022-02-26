package com.skidis.wordle

import scala.annotation.tailrec

trait XordleProcessor extends SolveStrategy with GuessRetriever with WordHintsRetriever with Writer {

  def hintProps: HintProps
  lazy val winningWordHints: WordHints = List.fill(hintProps.wordSize)(hintProps.inPosHint)

  def process(wordSet: WordSet, suggestion: String): List[(String, WordHints)] = {
    processRecurse(wordSet, suggestion)
  }

  @tailrec
  private def processRecurse(wordSet: WordSet, suggestion: String, guesses: List[(String, WordHints)] = Nil)
  : List[(String, WordHints)] = {

    writeLine(s"${List.fill(40)('*').mkString}")

    val (currentGuess, wordHints) =
      if (wordSet.size == 1) (suggestion, winningWordHints)
      else {
        val guess = retrieveGuess(suggestion)
        val pattern = retrieveWordHints(guess)
        (guess, pattern)
      }

    writeLine(s"${wordHints.mkString}")
    writeLine(s"Current Guess: $currentGuess, Guess #:${guesses.size +1}")
    if (wordSet.size == 1) writeLine("Only 1 choice left!!!")

    val updatedGuesses = guesses :+ (currentGuess, wordHints)

    if (wordHints.isEmpty) Nil // User entered an empty string, so abort without finishing
    else if (wordHints == winningWordHints) updatedGuesses // word hints are all in-position, so word has been guessed
    else if (updatedGuesses.size == 6) updatedGuesses :+ ("", Nil)
    else {
      // Eliminate words from set based on current guess and word hints
      val remainingWords = reduceWordSet(wordSet, currentGuess, wordHints)
      writeLine(s"Remaining Words: ${remainingWords.size}")

      // Determine next guess and start next iteration
      val (nextGuess, info) = generateNextGuess(remainingWords, hintProps)
      writeLine(info)
      processRecurse(remainingWords, nextGuess, updatedGuesses)
    }
  }
}
