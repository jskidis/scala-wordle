package com.skidis.wordle

import scala.annotation.tailrec

trait GuessInput extends LineReader with LineWriter with GuessValidator {
  def guessPromptMsg(suggestion: String) = s"Suggested Guess: $suggestion\nEnter Guess (or blank line to accept): "
  val guessErrorMsg = s"\nInvalid word, guess must be five characters and only contain letters\n"

  @tailrec
  final def getGuessFromInput(suggestion: String): String = {
    writeLine(guessPromptMsg(suggestion))
    val input = readLine().toUpperCase
    if (input.trim.isEmpty) suggestion.toUpperCase
    else if (validateGuess(input)) input
    else {
      writeLine(guessErrorMsg)
      getGuessFromInput(suggestion)
    }
  }
}

