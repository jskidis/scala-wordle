package com.skidis.wordle

import scala.annotation.tailrec

trait GuessInput {
  def guessPromptMsg(suggestion: String) = s"Suggested Guess: $suggestion\nEnter Guess (or blank line to accept): "
  val guessErrorMsg = s"\nInvalid word, guess must be five characters and only contain letters\n"

  @tailrec
  final def gatherGuess(reader: LineReader, writer: LineWriter, validator: Validator)(suggestion: String): String = {
    writer(guessPromptMsg(suggestion))
    val input = reader().toUpperCase
    if (input.trim.isEmpty) suggestion.toUpperCase
    else if (validator(input)) input
    else {
      writer(guessErrorMsg)
      gatherGuess(reader, writer, validator)(suggestion)
    }
  }
}

object GuessInput extends GuessInput
