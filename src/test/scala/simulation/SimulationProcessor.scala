package com.skidis.wordle
package simulation

import frequency.CachingWordColorPatternGenerator

abstract class SimulationProcessor(answer: String) extends XordleProcessor
  with CachingWordColorPatternGenerator
{
  override def retrieveColorPattern(guess: String): ColorPattern = generateStringColorPattern(answer, guess)
  override def retrieveGuess(suggestion: String): String = suggestion
  override def writeLine(line: String): Unit = {}
  override def writeString(s: String): Unit = {}
}
