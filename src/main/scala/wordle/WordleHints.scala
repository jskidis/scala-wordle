package com.skidis.wordle
package wordle

object WordleInPosHint extends InPosHint {
  override def inputChar: Char = 'G'
  override def colorBlock: String = "\uD83D\uDFE9"
}

object WordleInWordHint extends InWordHint {
  override def inputChar: Char = 'Y'
  override def colorBlock: String = "\uD83D\uDFE8"
}

object WordleMissHint extends MissHint {
  override def inputChar: Char = 'B'
  override def colorBlock: String = "\u2B1C"
}

trait WordleHintProps extends HintProps {
  override def inPosHint: InPosHint = WordleInPosHint
  override def inWordHint: InWordHint = WordleInWordHint
  override def missHint: MissHint = WordleMissHint
}
object WordleHintProps extends WordleHintProps

