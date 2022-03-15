package com.skidis.wordle
package input

import scala.annotation.tailrec

trait GuessInput extends LineReader with Writer with GuessValidator {
  def topSuggestion(suggestions: Seq[String]): String =
    suggestions.headOption.getOrElse("").toUpperCase
    
  def guessPromptMsg(suggestions: Seq[String]) =
    s"Suggestions: ${suggestions.mkString(", ")}\nEnter Guess (or blank to use ${topSuggestion(suggestions)}): "

  @tailrec
  final def getGuessFromInput(suggestions: Seq[String]): String = {
    writeString(guessPromptMsg(suggestions))
    val input = readLine().toUpperCase

    if (input.trim.isEmpty) topSuggestion(suggestions)
    else validateGuess(input) match {
      case None => input
      case Some(errorMsg) =>
        writeLine(errorMsg)
        getGuessFromInput(suggestions)
    }
  }
}
