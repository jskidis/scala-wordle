package com.skidis.wordle
package input

import scala.annotation.tailrec

trait GuessInput extends LineReader with Writer with GuessValidator {
  def guessPromptMsg(suggestion: String) = s"Suggested Guess: $suggestion\nEnter Guess (or blank line to accept): "

  @tailrec
  final def getGuessFromInput(suggestion: String): String = {
    writeString(guessPromptMsg(suggestion))
    val input = readLine().toUpperCase

    if (input.trim.isEmpty) suggestion.toUpperCase
    else validateGuess(input) match {
      case None => input
      case Some(errorMsg) =>
        writeLine(errorMsg)
        getGuessFromInput(suggestion)
    }
  }
}
