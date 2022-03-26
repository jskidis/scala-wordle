package com.skidis.wordle
package nerdle

import runners._
import strategy._

trait NerdleRunner extends XrdleRunner {
  override def puzzleName: String = "nerdlegame"
  override def hardMode: Boolean = false
}

trait NerdleStandardWordSets extends GuessAndAnswerSets with NerdleGuessableGenerator {
  val equations: Set[NerdleEquation] = generate8CharEquations()
  override lazy val guessSet: WordSet = equations
  override lazy val answerSet: WordSet = equations
}

trait NerdleStandardRunner extends NerdleRunner with NerdleStandardWordSets
  with InteractiveProcessorFactory with SimulationProcessFactory with FirstGuessOptFactory{

  val firstGuess = "54-38=16"

  override def createInteractiveProcessor(): InteractiveProcessor = {
    new NerdleInteractiveProcessor with ClusterAndCharFreqStrategy with FixedGuessesStrategy {
      override def fixedGuesses: Seq[String] = Seq(firstGuess)
    }
  }
  override def createSimulationProcessor(startGuesses: Seq[String] = Nil): SimulationProcessor  = {
    new NerdleSimulationProcessor with NerdleProcessor with ClusterAndCharFreqStrategyCaching with FixedGuessesStrategy {
      override def fixedGuesses: Seq[String] = {
        if (startGuesses == Nil) Seq(firstGuess) else startGuesses
      }
    }
  }
  def createFirstGuessOptimizer(): FirstGuessOptimizer = {
    new NerdleFirstGuessOptimizer with ClusterAndCharFreqStrategy with NerdleStandardWordSets
  }
}


trait MiniNerdleWordSets extends GuessAndAnswerSets with NerdleGuessableGenerator {
  val equations: Set[NerdleEquation] = generate6CharEquations()
  override lazy val guessSet: WordSet = equations
  override lazy val answerSet: WordSet = equations
}

trait MiniNerdleRunner extends NerdleRunner with MiniNerdleWordSets
  with InteractiveProcessorFactory with SimulationProcessFactory with FirstGuessOptFactory {

  val firstGuess = "28/7=4"
  override def puzzleName: String = "mini nerdlegame"

  override def createInteractiveProcessor(): InteractiveProcessor = {
    new MiniNerdleInteractiveProcessor with ClusterStrategy with FixedGuessesStrategy {
      override def fixedGuesses: Seq[String] = Seq(firstGuess)
    }
  }
  override def createSimulationProcessor(startGuesses: Seq[String] = Nil): SimulationProcessor  = {
    new MiniNerdleSimulationProcessor with ClusterStrategyCaching with FixedGuessesStrategy {
      override def fixedGuesses: Seq[String] = {
        if (startGuesses == Nil) Seq(firstGuess) else startGuesses
      }
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
  override def createSimulationProcessor(startGuesses: Seq[String] = Nil): SimulationProcessor  = {
    new NerdleSimulationProcessor with NerdleProcessor with RandomGuessStrategy
  }
}


object NerdleInteractiveStandardRunner extends App
  with XrdleInteractiveRunner with NerdleStandardRunner {
  runInteractive()
}

object NerdleInteractiveMiniRunner extends App
  with XrdleInteractiveRunner with MiniNerdleRunner {
  runInteractive()
}

object NerdleInteractiveRandomRunner extends App
  with XrdleInteractiveRunner with NerdleRandomGuessRunner {
  runInteractive()
}


trait NerdleSimulationStandardRunner extends XrdleSimulationRunner with NerdleStandardRunner {
  def main(args: Array[String]):Unit = printResults(runSimulation())
}
object NerdleSimulationStandardRunner extends NerdleSimulationStandardRunner

trait NerdleSimulationMiniRunner extends XrdleSimulationRunner with MiniNerdleRunner {
  def main(args: Array[String]):Unit = printResults(runSimulation())
}
object NerdleSimulationMiniRunner extends NerdleSimulationMiniRunner

object NerdleFirstGuessOptStandardRunner extends App
  with FirstGuessRunner with NerdleStandardRunner {
  runOptimizer(12)
}

object NerdleFirstGuessOptMiniRunner extends App
  with FirstGuessRunner with MiniNerdleRunner {
  runOptimizer(12)
}
