package com.skidis.wordle

import scala.annotation.tailrec

trait GuessInput extends LineReader with Writer with GuessValidator {
  def guessPromptMsg(suggestion: String) = s"Suggested Guess: $suggestion\nEnter Guess (or blank line to accept): "
  val guessErrorMsg = s"Invalid word, guess must be five characters and only contain letters"

  @tailrec
  final def getGuessFromInput(suggestion: String): String = {
    writeString(guessPromptMsg(suggestion))
    val input = readLine().toUpperCase
    if (input.trim.isEmpty) suggestion.toUpperCase
    else if (validateGuess(input)) input
    else {
      writeLine(guessErrorMsg)
      getGuessFromInput(suggestion)
    }
  }
}
