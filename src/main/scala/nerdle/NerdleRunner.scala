package com.skidis.wordle
package nerdle

import strategy._

import scala.util.Random

trait NerdleRunner extends XordleRunner {
  override def puzzleName: String = "nerdlegame"
}


trait NerdleStandardWordSets extends GuessAndAnswerSets with NerdleGuessableGenerator {
  val equations: Set[NerdleEquation] = generate8CharEquations()
  override lazy val guessSet: WordSet = generateWithFrequencies(equations)
  override lazy val answerSet: WordSet = equations
}

trait NerdleStandardRunner extends NerdleRunner with NerdleStandardWordSets {
  override def startGuess: String = "54-38=16"

  override def createInteractiveProcessor(): InteractiveProcessor = {
    new NerdleInteractiveProcessor with ClusterAndFreqStrategy
  }
  override def createSimulationProcessor(): SimulationProcessor = {
    new NerdleSimulationProcessor with NerdleProcessor with ClusterAndFreqStrategyCaching
  }
  def createFirstGuessOptimizer(): FirstGuessOptimizer = {
    new NerdleFirstGuessOptimizer with ClusterAndFreqStrategy with NerdleStandardWordSets
  }
}


trait MiniNerdleWordSets extends GuessAndAnswerSets with NerdleGuessableGenerator {
  val equations: Set[NerdleEquation] = generate6CharEquations()
  override lazy val guessSet: WordSet = equations
  override lazy val answerSet: WordSet = equations
}

trait MiniNerdleRunner extends NerdleRunner with MiniNerdleWordSets {
  override def puzzleName: String = "mini nerdlegame"
  override def startGuess: String = "28/7=4"

  override def createInteractiveProcessor(): InteractiveProcessor = {
    new MiniNerdleInteractiveProcessor with ClusterStrategy
  }
  override def createSimulationProcessor(): SimulationProcessor = {
    new MiniNerdleSimulationProcessor with ClusterStrategyCaching
  }
  override def createFirstGuessOptimizer(): FirstGuessOptimizer = {
    new MiniNerdleFirstGuessOptimizer with ClusterStrategy with MiniNerdleWordSets
  }
}


trait NerdleRandomGuessRunner extends NerdleRunner with NerdleStandardWordSets {
  override def puzzleName: String = "nerdlegame"


  override def startGuess: String = {
    Random.shuffle(guessSet.toVector).headOption.map(_.phrase).getOrElse("10+10=20")
  }

  override def createInteractiveProcessor(): InteractiveProcessor = {
    new NerdleInteractiveProcessor with RandomGuessStrategy
  }
  override def createSimulationProcessor(): SimulationProcessor = {
    new NerdleSimulationProcessor with NerdleProcessor with RandomGuessStrategy
  }
  def createFirstGuessOptimizer(): FirstGuessOptimizer = {
    new NerdleFirstGuessOptimizer with RandomGuessStrategy with NerdleStandardWordSets
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

object NerdleInteractiveRandomRunner extends App
  with XordleInteractiveRunner with NerdleRandomGuessRunner {
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

object NerdleSimulationRandomRunner extends App
  with XordleSimulationRunner with NerdleRandomGuessRunner {
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
