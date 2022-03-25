package com.skidis.wordle
package strategy

// The WordHintsGenerator needs hint props, but in term of the ClusterGenerator it doesn't really need specific hints
// (i.e., Wordle Hints vs Nerdle Hints) because they are used to generate unique clusters and then discarded
trait GenericHintProps extends HintProps {
  class GenericHint(ch: Char) {
    def inputChar: Char = ch
    def colorBlock: String = ch.toString
  }
  val iph = new GenericHint(ch = 'G') with InPosHint
  val iwh = new GenericHint(ch = 'Y') with InWordHint
  val msh = new GenericHint(ch = 'B') with MissHint

  override def inPosHint: InPosHint = iph
  override def inWordHint: InWordHint = iwh
  override def missHint: MissHint = msh
}
