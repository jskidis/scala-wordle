package com.skidis.wordle
package simulation

import frequency.CachingWordHintsGenerator

abstract class SimulationProcessor(answer: String) extends XordleProcessor with CachingWordHintsGenerator {
  override def retrieveWordHints(guess: String): WordHints = generateWordWordHints(answer, guess)
  override def retrieveGuess(suggestion: String): String = suggestion
  override def writeLine(line: String): Unit = {}
  override def writeString(s: String): Unit = {}
}
