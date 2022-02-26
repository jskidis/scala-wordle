package com.skidis.wordle

object AInPosHint extends InPosHint {
  override def inputChar: Char = 'G'
  override def colorBlock: String = "\uD83D\uDFE9"
}

object AInWordHint extends InWordHint {
  override def inputChar: Char = 'Y'
  override def colorBlock: String = "\uD83D\uDFE8"
}

object AMissHint extends MissHint {
  override def inputChar: Char = 'B'
  override def colorBlock: String = "\u2B1C"
}

trait TestHintProps extends HintProps {
  override def inPosHint: InPosHint = AInPosHint
  override def inWordHint: InWordHint = AInWordHint
  override def missHint: MissHint = AMissHint
  override def wordSize: Int = 5
}
object TestHintProps extends TestHintProps
