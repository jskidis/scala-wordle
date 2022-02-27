package com.skidis.wordle
package nerdle

import strategy.ClusterStrategy

trait NerdleRunner extends XordleRunner {
  override def puzzleName: String = "nerdlegame"
}

trait NerdleStandardRunner extends NerdleRunner with NerdleGuessableGenerator {
  override def startPhrase: String = "58-42=16"
  override lazy val guessSet: WordSet = generatateEquations()
  override def createStaticProcessor(): XordleProcessor = new NerdleInteractiveProcessor with ClusterStrategy
}

object NerdleStandardInteractiveRunner extends App
  with XordleInteractiveRunner with NerdleStandardRunner {
  runInteractive()
}
