package com.skidis.wordle
package nerdle.runner

import nerdle._
import runners._

trait NerdleRunner extends XrdleRunner {
  override def puzzleName: String = "nerdlegame"
  override def hardMode: Boolean = false
}

trait NerdleProcessor extends NerdleHintProps with NerdleGuessProps with NerdleInputValidator
trait NerdleProcessorMiniProps extends NerdleHintProps with MiniNerdleGuessProps with NerdleInputValidator

