package com.skidis.wordle
package nerdle

object NerdleInPosHint extends InPosHint {
  override def inputChar: Char = 'G'
  override def colorBlock: String = "\uD83D\uDFE9"
}

object NerdleInWordHint extends InWordHint {
  override def inputChar: Char = 'R'
  override def colorBlock: String = "\uD83D\uDFEA"
}

object NerdleMissHint extends MissHint {
  override def inputChar: Char = 'B'
  override def colorBlock: String = "\u2B1B"
}

trait NerdleHintProps extends HintProps {
  override def inPosHint: InPosHint = NerdleInPosHint
  override def inWordHint: InWordHint = NerdleInWordHint
  override def missHint: MissHint = NerdleMissHint
}
object NerdleHintProps extends NerdleHintProps

