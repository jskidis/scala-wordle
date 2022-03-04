package com.skidis.wordle

import scala.annotation.tailrec

trait XordleProcessor extends SolveStrategy with GuessRetriever with WordHintsRetriever
  with GuessProps with HintProps with Writer {

  lazy val winningWordHints: WordHints = Seq.fill(guessWordLength)(inPosHint)

  def process(wordSet: WordSet, suggestion: String): Seq[(String, WordHints)] = {
    processRecurse(wordSet, suggestion)
  }

  def process(wordSet: WordSet, suggestion: String, answer: String): Seq[(String, WordHints)] = {
    processRecurse(wordSet, suggestion, Option(answer))
  }

  @tailrec
  private def processRecurse(wordSet: WordSet, suggestion: String, answer: Option[String] = None, guesses: Seq[(String, WordHints)] = Nil)
  : Seq[(String, WordHints)] = {

    writeLine(s"${Seq.fill(40)('*').mkString}")

    val (currentGuess, wordHints) =
      if (wordSet.size == 1) (suggestion, winningWordHints)
      else {
        val guess = retrieveGuess(suggestion)
        val pattern = retrieveWordHints(guess, answer)
        (guess, pattern)
      }

    writeLine(s"${wordHints.mkString}")
    writeLine(s"Current Guess: $currentGuess, Guess #:${guesses.size +1}")
    if (wordSet.size == 1) writeLine("Only 1 choice left!!!")

    val updatedGuesses = guesses :+ (currentGuess, wordHints)

    if (wordHints.isEmpty) Nil // User entered an empty string, so abort without finishing
    else if (wordHints == winningWordHints) updatedGuesses // word hints are all in-position, so word has been guessed
    else if (updatedGuesses.size == maxGuesses) updatedGuesses :+ ("", Nil)
    else {
      // Eliminate words from set based on current guess and word hints
      val remainingWords = reduceWordSet(wordSet, currentGuess, wordHints)
      writeLine(s"Remaining Words: ${remainingWords.size}")

      // Determine next guess and start next iteration
      generateNextGuess(remainingWords) match {
        case None => Nil
        case Some(guess: XordlePhrase) => processRecurse(remainingWords, guess.phrase, answer, updatedGuesses)
      }
    }
  }
}
