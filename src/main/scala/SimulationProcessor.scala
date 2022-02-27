package com.skidis.wordle

abstract class SimulationProcessor(answer: String) extends XordleProcessor with WordHintsGenerator {
  override def retrieveWordHints(guess: String): WordHints = generateWordWordHints(answer, guess)
  override def retrieveGuess(suggestion: String): String = suggestion
  override def writeLine(line: String): Unit = {}
  override def writeString(s: String): Unit = {}
  override def maxGuesses: Int = 6
}
