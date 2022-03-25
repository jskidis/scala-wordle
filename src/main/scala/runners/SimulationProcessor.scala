package com.skidis.wordle
package runners

import hintgen.WordHintsGenerator

trait SimulationProcessor extends XrdleProcessor with NullWriter
  with WordHintsGenerator {

  override def retrieveWordHints(guess: String, answer: Option[String]): WordHints =
    generateWordHints(answer.getOrElse(""), guess)

  override def retrieveGuess(suggestions: Seq[String]): String = suggestions.headOption.getOrElse("")
}
