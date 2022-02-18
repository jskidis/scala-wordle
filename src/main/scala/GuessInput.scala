package com.skidis.wordle

import scala.annotation.tailrec

object GuessInput {
  def promptMsg(suggestion: String) = s"Suggested Guess: $suggestion\nEnter Guess (or blank line to accept): "
  val errorMsg = s"\nInvalid word, guess must be five characters and only contain letters\n"

  @tailrec
  def gatherGuess(reader: LineReader, writer: LineWriter, validator: Validator)(suggestion: String): String = {
    writer(promptMsg(suggestion))
    val input = reader().toUpperCase
    if (input.trim.isEmpty) suggestion.toUpperCase
    else if (validator(input)) input
    else {
      writer(errorMsg)
      gatherGuess(reader, writer, validator)(suggestion)
    }
  }
}
