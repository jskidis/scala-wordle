package com.skidis.wordle

trait SimulationProcessor extends XordleProcessor with NullWriter
  with WordHintsGenerator {

  override def retrieveWordHints(guess: String, answer: Option[String]): WordHints =
    generateWordHints(answer.getOrElse(""), guess)

  override def retrieveGuess(suggestion: String): String = suggestion
}
