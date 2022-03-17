package com.skidis.wordle

import scala.annotation.tailrec

trait XrdleProcessor extends SolveStrategy with GuessRetriever with WordHintsRetriever
  with GuessProps with HintProps with Writer {

  val userAbortMsg = "User aborted"
  val exhaustedWordsMsg = "Unexpectedly ran out of words"

  lazy val winningWordHints: WordHints = Seq.fill(guessWordLength)(inPosHint)

  def process(wordSet: WordSet): Either[String, Seq[(String, WordHints)]] = {
    processRecurse(wordSet)
  }

  def process(wordSet: WordSet, answer: String): Either[String, Seq[(String, WordHints)]] = {
    processRecurse(wordSet, Option(answer))
  }

  @tailrec
  private def processRecurse(wordSet: WordSet, answer: Option[String] = None, guesses: Seq[(String, WordHints)] = Nil)
  : Either[String, Seq[(String, WordHints)]] = {

    writeLine(s"${Seq.fill(40)('*').mkString}")

    lazy val suggestions = generateNextGuesses(wordSet, guesses, numSuggestions)
    if (wordSet.size == 1) {
      writeLine("Only 1 choice left!!!")
      writeLine(s"Current Guess: ${wordSet.head.text}, Guess #:${guesses.size +1}")
      Right(guesses :+ (wordSet.head.text, winningWordHints))
    }
    else if (suggestions.isEmpty) Left(exhaustedWordsMsg)
    else {
      val currentGuess = retrieveGuess(suggestions)
      val wordHints = retrieveWordHints(currentGuess, answer)

      writeLine(s"${wordHints.mkString}")
      writeLine(s"Current Guess: $currentGuess, Guess #:${guesses.size +1}")

      val updatedGuesses = guesses :+ (currentGuess, wordHints)

      if (wordHints.isEmpty) Left(userAbortMsg) // User entered an empty string, so abort without finishing
      else if (wordHints == winningWordHints) Right(updatedGuesses) // word hints are all in-position, so word has been guessed
      else if (updatedGuesses.size == maxGuesses) Right(updatedGuesses :+ ("", Nil))
      else {
        // Eliminate words from set based on current guess and word hints
        val remainingWords = reduceWordSet(wordSet, currentGuess, wordHints)
        writeLine(s"Remaining Words: ${remainingWords.size}")
        processRecurse(remainingWords, answer, updatedGuesses)
      }
    }
  }
}
