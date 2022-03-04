package com.skidis.wordle

import scala.annotation.tailrec

trait XordleProcessor extends SolveStrategy with GuessRetriever with WordHintsRetriever
  with GuessProps with HintProps with Writer {

  val userAbortMsg = "User aborted"
  val exhaustedWordsMsg = "Unexpectedly ran out of words"

  lazy val winningWordHints: WordHints = Seq.fill(guessWordLength)(inPosHint)

  def process(wordSet: WordSet, suggestion: String): Either[String, Seq[(String, WordHints)]] = {
    processRecurse(wordSet, Vector(suggestion))
  }

  def process(wordSet: WordSet, suggestion: String, answer: String): Either[String, Seq[(String, WordHints)]] = {
    processRecurse(wordSet, Vector(suggestion), Option(answer))
  }

  @tailrec
  private def processRecurse(wordSet: WordSet, suggestions: Vector[String], answer: Option[String] = None,
    guesses: Seq[(String, WordHints)] = Nil)
  : Either[String, Seq[(String, WordHints)]] = {

    writeLine(s"${Seq.fill(40)('*').mkString}")

    val (currentGuess, wordHints) =
      if (wordSet.size == 1) (suggestions.headOption.getOrElse(""), winningWordHints)
      else {
        val guess = retrieveGuess(suggestions)
        val pattern = retrieveWordHints(guess, answer)
        (guess, pattern)
      }

    writeLine(s"${wordHints.mkString}")
    writeLine(s"Current Guess: $currentGuess, Guess #:${guesses.size +1}")
    if (wordSet.size == 1) writeLine("Only 1 choice left!!!")

    val updatedGuesses = guesses :+ (currentGuess, wordHints)

    if (wordHints.isEmpty) Left(userAbortMsg) // User entered an empty string, so abort without finishing
    else if (wordHints == winningWordHints) Right(updatedGuesses) // word hints are all in-position, so word has been guessed
    else if (updatedGuesses.size == maxGuesses) Right(updatedGuesses :+ ("", Nil))
    else {
      // Eliminate words from set based on current guess and word hints
      val remainingWords = reduceWordSet(wordSet, currentGuess, wordHints)
      writeLine(s"Remaining Words: ${remainingWords.size}")

      // Determine next guess and start next iteration
      val guesses = generateNextGuesses(remainingWords, numSuggestions)
      if (guesses.isEmpty) Left(exhaustedWordsMsg)
      else processRecurse(remainingWords, guesses.map(_.phrase), answer, updatedGuesses)
    }
  }
}
