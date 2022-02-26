package com.skidis.wordle
package simulation

import frequency.CachingWordHintsGenerator

abstract class SimulationProcessor(answer: String, hintPropsVal: HintProps) extends XordleProcessor
  with CachingWordHintsGenerator
{
  override def hintProps: HintProps = hintPropsVal
  override def retrieveWordHints(guess: String): WordHints = generateWordWordHints(answer, guess, hintProps)
  override def retrieveGuess(suggestion: String): String = suggestion
  override def writeLine(line: String): Unit = {}
  override def writeString(s: String): Unit = {}
}
