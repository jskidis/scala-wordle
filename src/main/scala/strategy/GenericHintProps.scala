package com.skidis.wordle
package strategy

// The WordHintsGenerator needs hint props, but in term of the ClusterGenerator it doesn't really need specific hints
// (i.e., Wordle Hints vs Nerdle Hints) because they are used to generate unique clusters and then discarded
trait GenericHintProps extends HintProps {
  case class GenericInPosHint() extends InPosHint {
    def inputChar: Char = 'G'
    def colorBlock: String = "G"
  }
  case class GenericInWordHint() extends InWordHint {
    def inputChar: Char = 'Y'
    def colorBlock: String = "Y"
  }
  case class GenericMissHint() extends MissHint {
    def inputChar: Char = 'B'
    def colorBlock: String = "B"
  }

  override def inPosHint: InPosHint = GenericInPosHint()
  override def inWordHint: InWordHint = GenericInWordHint()
  override def missHint: MissHint = GenericMissHint()
}
