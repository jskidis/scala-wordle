package com.skidis.wordle

trait HintBlock {
  def inputChar: Char
  def colorBlock: String
  override def toString: String = colorBlock
}

trait InPosHint extends HintBlock
trait InWordHint extends HintBlock
trait MissHint extends HintBlock

trait HintProps {
  def inPosHint: InPosHint
  def inWordHint: InWordHint
  def missHint: MissHint

  lazy val inputChars: Seq[Char] = Seq(
    inPosHint.inputChar.toLower, inWordHint.inputChar.toLower, missHint.inputChar.toLower,
    inPosHint.inputChar.toUpper, inWordHint.inputChar.toUpper, missHint.inputChar.toUpper
  )
}

