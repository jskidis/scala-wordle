package com.skidis.wordle

abstract class SimulationProcessor(answer: String) extends XordleProcessor with NullWriter
  with WordHintsGenerator {

  override def retrieveWordHints(guess: String): WordHints = generateWordWordHints(answer, guess)
  override def retrieveGuess(suggestion: String): String = suggestion
  override def maxGuesses: Int = 6
}
