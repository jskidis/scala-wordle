package com.skidis.wordle

import input.{GuessInput, ResultInput}

trait InteractiveProcessor extends XrdleProcessor with ConsoleWriter with StdInLineReader
  with GuessInput with ResultInput {

  override def retrieveGuess(suggestions: Seq[String]): String = getGuessFromInput(suggestions)
  override def retrieveWordHints(guess: String, answer: Option[String]): WordHints = generatePattern()
}



