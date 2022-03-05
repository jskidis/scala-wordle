package com.skidis.wordle
package nerdle

import strategy.ClusterStrategy

trait NerdleRunner extends XordleRunner {
  override def puzzleName: String = "nerdlegame"
}


trait NerdleStandardWordSets extends GuessAndAnswerSets with NerdleGuessableGenerator {
  override lazy val guessSet: WordSet = generate8CharEquations()
  override lazy val answerSet: WordSet = generate8CharEquations()
}

trait NerdleStandardRunner extends NerdleRunner with NerdleStandardWordSets {
  override def startGuess: String = "59-42=17"

  override def createInteractiveProcessor(): InteractiveProcessor = {
    new NerdleInteractiveProcessor with ClusterStrategy
  }
  override def createSimulationProcessor(): SimulationProcessor = {
    new NerdleSimulationProcessor with NerdleProcessor with ClusterStrategy
  }
  def createFirstGuessOptimizer(): FirstGuessOptimizer = {
    new NerdleFirstGuessOptimizer with ClusterStrategy with NerdleStandardWordSets
  }
}


trait MiniNerdleWordSets extends GuessAndAnswerSets with NerdleGuessableGenerator {
  override lazy val guessSet: WordSet = generate6CharEquations()
  override lazy val answerSet: WordSet = generate6CharEquations()
}

trait MiniNerdleRunner extends NerdleRunner with MiniNerdleWordSets {
  override def puzzleName: String = "mini nerdlegame"
  override def startGuess: String = "28/7=4"

  override def createInteractiveProcessor(): InteractiveProcessor = {
    new MiniNerdleInteractiveProcessor with ClusterStrategy
  }
  override def createSimulationProcessor(): SimulationProcessor = {
    new MiniNerdleSimulationProcessor with ClusterStrategy
  }
  override def createFirstGuessOptimizer(): FirstGuessOptimizer = {
    new MiniNerdleFirstGuessOptimizer with ClusterStrategy with MiniNerdleWordSets
  }
}


object NerdleInteractiveStandardRunner extends App
  with XordleInteractiveRunner with NerdleStandardRunner {
  runInteractive()
}

object NerdleInteractiveMiniRunner extends App
  with XordleInteractiveRunner with MiniNerdleRunner {
  runInteractive()
}


object NerdleSimulationStandardRunner extends App
  with XordleSimulationRunner with NerdleStandardRunner {
  runSimulation()
}


object NerdleSimulationMiniRunner extends App
  with XordleSimulationRunner with MiniNerdleRunner {
  runSimulation()
}


object NerdleFirstGuessOptStandardRunner extends App
  with FirstGuessRunner with NerdleStandardRunner {
  runOptimizer(12)
}

object NerdleFirstGuessOptMiniRunner extends App
  with FirstGuessRunner with MiniNerdleRunner {
  runOptimizer(12)
}
