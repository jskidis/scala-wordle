package com.skidis.wordle
package wordle.runner

import runners._
import wordle.{WordleGuessProps, WordleHintProps, WordleInputValidator}

trait WordleRunner extends XrdleRunner with WordleGuessProps {
  override def puzzleName: String = "Wordle"
  override def hardMode: Boolean = true
}

trait WordleProcessor extends WordleHintProps with WordleGuessProps with WordleInputValidator
