package com.skidis.wordle
package nerdle

import strategy.ClusterStrategy

trait NerdleRunner extends XordleRunner {
  override def puzzleName: String = "nerdlegame"
}

trait NerdleStandardRunner extends NerdleRunner with NerdleGuessableGenerator {
  override def startGuess: String = "58-42=16"
  override lazy val guessSet: WordSet = generate8CharEquations()
  override lazy val answerSet: WordSet = generate8CharEquations()

  override def createInteractiveProcessor(): InteractiveProcessor = {
    new NerdleInteractiveProcessor with ClusterStrategy
  }
  override def createSimulationProcessor(answer: String): SimulationProcessor = {
    new SimulationProcessor(answer) with NerdleProcessor with ClusterStrategy
  }
}

trait MiniNerdleRunner extends NerdleRunner with NerdleGuessableGenerator {
  override def puzzleName: String = "mini nerdlegame"
  override def startGuess: String = "4*7=28"
  override lazy val guessSet: WordSet = generate6CharEquations()
  override lazy val answerSet: WordSet = generate6CharEquations()

  override def createInteractiveProcessor(): InteractiveProcessor = {
    new MiniNerdleInteractiveProcessor with ClusterStrategy
  }
  override def createSimulationProcessor(answer: String): SimulationProcessor = {
    new SimulationProcessor(answer) with MiniNerdleProcessor with ClusterStrategy
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

object NerdleInteractiveMiniRunner extends App
  with XordleInteractiveRunner with MiniNerdleRunner {
  runInteractive()
}

object NerdleSimulationMiniRunner extends App
  with XordleSimulationRunner with MiniNerdleRunner {
  runSimulation()
}
