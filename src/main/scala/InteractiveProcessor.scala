package com.skidis.wordle

import input.{GuessInput, ResultInput}

trait InteractiveProcessor extends XordleProcessor with ConsoleWriter with StdInLineReader
  with GuessInput with ResultInput {

  override def retrieveGuess(suggestion: String): String = getGuessFromInput(suggestion)
  override def retrieveWordHints(guess: String, answer: Option[String]): WordHints = generatePattern()
}



