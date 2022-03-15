package com.skidis.wordle
package nerdle

import strategy._

trait NerdleRunner extends XordleRunner {
  override def puzzleName: String = "nerdlegame"
}

trait NerdleStandardWordSets extends GuessAndAnswerSets with NerdleGuessableGenerator {
  val equations: Set[NerdleEquation] = generate8CharEquations()
  override lazy val guessSet: WordSet = generateWithFrequencies(equations)
  override lazy val answerSet: WordSet = equations
}

trait NerdleStandardRunner extends NerdleRunner with NerdleStandardWordSets
  with InteractiveProcessorFactory with SimulationProcessFactory with FirstGuessOptFactory{

  val startGuess = "54-38=16"

  override def createInteractiveProcessor(): InteractiveProcessor = {
    new NerdleInteractiveProcessor with ClusterAndFreqStrategy with FixedGuessesStrategy {
      override def fixedGuesses: Seq[String] = Seq(startGuess)
    }
  }
  override def createSimulationProcessor(): SimulationProcessor = {
    new NerdleSimulationProcessor with NerdleProcessor with ClusterAndFreqStrategyCaching with FixedGuessesStrategy {
      override def fixedGuesses: Seq[String] = Seq(startGuess)
    }
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

trait MiniNerdleRunner extends NerdleRunner with MiniNerdleWordSets
  with InteractiveProcessorFactory with SimulationProcessFactory with FirstGuessOptFactory {

  val startGuess = "28/7=4"
  override def puzzleName: String = "mini nerdlegame"

  override def createInteractiveProcessor(): InteractiveProcessor = {
    new MiniNerdleInteractiveProcessor with ClusterStrategy with FixedGuessesStrategy {
      override def fixedGuesses: Seq[String] = Seq(startGuess)
    }
  }
  override def createSimulationProcessor(): SimulationProcessor = {
    new MiniNerdleSimulationProcessor with ClusterStrategyCaching with FixedGuessesStrategy {
      override def fixedGuesses: Seq[String] = Seq(startGuess)
    }
  }
  override def createFirstGuessOptimizer(): FirstGuessOptimizer = {
    new MiniNerdleFirstGuessOptimizer with ClusterStrategy with MiniNerdleWordSets
  }
}


trait NerdleRandomGuessRunner extends NerdleRunner with NerdleStandardWordSets
  with InteractiveProcessorFactory with SimulationProcessFactory {
  override def puzzleName: String = "nerdlegame"

  override def createInteractiveProcessor(): InteractiveProcessor = {
    new NerdleInteractiveProcessor with RandomGuessStrategy
  }
  override def createSimulationProcessor(): SimulationProcessor = {
    new NerdleSimulationProcessor with NerdleProcessor with RandomGuessStrategy
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
