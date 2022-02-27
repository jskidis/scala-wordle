package com.skidis.wordle
package nerdle

import strategy.ClusterStrategy

trait NerdleRunner extends XordleRunner {
  override def puzzleName: String = "nerdlegame"
}

trait NerdleStandardRunner extends NerdleRunner with NerdleGuessableGenerator {
  override def startGuess: String = "58-42=16"
  override lazy val guessSet: WordSet = generatateEquations()
  override lazy val answerSet: WordSet = generatateEquations()

  override def createInteractiveProcessor(): InteractiveProcessor = {
    new NerdleInteractiveProcessor with ClusterStrategy
  }
  override def createSimulationProcessor(answer: String): SimulationProcessor = {
    new SimulationProcessor(answer) with NerdleProcessor with ClusterStrategy
  }
}

object NerdleInteractiveStandardRunner extends App
  with XordleInteractiveRunner with NerdleStandardRunner {
  runInteractive()
}

object NerdleSimulationStandardRunner extends App
  with XordleSimulationRunner with NerdleStandardRunner {
  runSimulation()
}
