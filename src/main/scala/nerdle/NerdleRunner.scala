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
  override def startGuess: String = "58-42=16"

  override def createInteractiveProcessor(): InteractiveProcessor = {
    new NerdleInteractiveProcessor with ClusterStrategy
  }
  override def createSimulationProcessor(answer: String): SimulationProcessor = {
    new NerdleSimulationProcessor(answer) with NerdleProcessor with ClusterStrategy
  }
  def createFirstGuessOptimizer(): FirstGuessOptimizator = {
    new NerdleFirstGuessOptimizer with ClusterStrategy with NerdleStandardWordSets
  }
}


trait MiniNerdleWordSets extends GuessAndAnswerSets with NerdleGuessableGenerator {
  override lazy val guessSet: WordSet = generate8CharEquations()
  override lazy val answerSet: WordSet = generate8CharEquations()
}

trait MiniNerdleRunner extends NerdleRunner with MiniNerdleWordSets {
  override def puzzleName: String = "mini nerdlegame"
  override def startGuess: String = "4*7=28"

  override def createInteractiveProcessor(): InteractiveProcessor = {
    new MiniNerdleInteractiveProcessor with ClusterStrategy
  }
  override def createSimulationProcessor(answer: String): SimulationProcessor = {
    new NerdleSimulationProcessor(answer) with ClusterStrategy
  }
  override def createFirstGuessOptimizer(): FirstGuessOptimizator = {
    new NerdleFirstGuessOptimizer with ClusterStrategy with MiniNerdleWordSets
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
  with XordleInteractiveRunner with NerdleStandardRunner {
  runInteractive()
}

object NerdleFirstGuessInteractiveMiniRunner extends App
  with XordleInteractiveRunner with MiniNerdleRunner {
  runInteractive()
}
