package com.skidis.wordle
package wordle

import strategy._

import scala.io.Source

trait WordleRunner extends XordleRunner {
  override def puzzleName: String = "Wordle"
}

trait WordleAnswerSet extends WordReader {
  lazy val answerSet: WordSet = readWords(Source.fromResource("answers.txt"))
}

trait WordleStandardWordSets extends WordleAnswerSet with GuessAndAnswerSets with WordReader {
  override lazy val guessSet: WordSet = readWordFrequencies(Source.fromResource("word-frequency-filtered.txt"))
}

trait WordleAnswerOnlyWordSets extends WordleAnswerSet with GuessAndAnswerSets with WordReader {
  override lazy val guessSet: WordSet = readWords(Source.fromResource("answers.txt"))
}


trait WordleStandardRunner extends WordleRunner with WordleStandardWordSets
  with InteractiveProcessorFactory with SimulationProcessFactory with FirstGuessOptFactory {
  val startGuess = "SLATE"

  override def createInteractiveProcessor(): InteractiveProcessor = {
    new WordleInteractiveProcessor with ClusterAndFreqStrategy with FixedGuessesStrategy {
      override def fixedGuesses: Seq[String] = Seq(startGuess)
    }
  }
  override def createSimulationProcessor(): SimulationProcessor  = {
    new WordleSimulationProcessor with WordleProcessor with ClusterAndFreqStrategyCaching with FixedGuessesStrategy {
      override def fixedGuesses: Seq[String] = Seq(startGuess)
    }
  }
  override def createFirstGuessOptimizer(): FirstGuessOptimizer = {
    new WordleFirstGuessOptimizer with ClusterAndFreqStrategy with WordleStandardWordSets
  }
}


trait WordleAnswerOnlyRunner extends WordleRunner with WordleAnswerOnlyWordSets
  with InteractiveProcessorFactory with SimulationProcessFactory with FirstGuessOptFactory {

  val startGuess = "SLATE"

  override def createInteractiveProcessor(): InteractiveProcessor = {
    new WordleInteractiveProcessor with ClusterStrategy with FixedGuessesStrategy {
      override def fixedGuesses: Seq[String] = Seq(startGuess)
    }
  }
  override def createSimulationProcessor(): SimulationProcessor  = {
    new WordleSimulationProcessor with WordleProcessor with ClusterStrategyCaching with FixedGuessesStrategy {
      override def fixedGuesses: Seq[String] = Seq(startGuess)
    }
  }
  override def createFirstGuessOptimizer(): FirstGuessOptimizer = {
    new WordleFirstGuessOptimizer with ClusterStrategy with WordleAnswerOnlyWordSets
  }
}


trait WordleCharFreqRunner extends WordleRunner with WordleAnswerOnlyWordSets
  with InteractiveProcessorFactory with SimulationProcessFactory {

  val startingGuesses = Seq("SLATE", "CORNY")

  override def createInteractiveProcessor(): InteractiveProcessor = {
    new WordleInteractiveProcessor with CharFreqStrategy with ClusterStrategyCaching with FixedGuessesStrategy {
      override def fixedGuesses: Seq[String] = startingGuesses
    }
  }
  override def createSimulationProcessor(): SimulationProcessor  = {
    new WordleSimulationProcessor with CharFreqStrategy with ClusterStrategyCaching with FixedGuessesStrategy {
      override def fixedGuesses: Seq[String] = startingGuesses
    }
  }
}


trait WordleReverseRunner extends WordleRunner with WordleStandardWordSets
  with InteractiveProcessorFactory with SimulationProcessFactory with FirstGuessOptFactory {

  val startGuess = "JAZZY"
  override lazy val answerSet: WordSet =  readWords(Source.fromResource("answers.txt")).take(100)

  override def createInteractiveProcessor(): InteractiveProcessor = {
    new WordleInteractiveProcessor with ReverseClusterStrategy with FixedGuessesStrategy {
      override def fixedGuesses: Seq[String] = Seq(startGuess)
    }
  }
  override def createSimulationProcessor(): SimulationProcessor  = {
    new WordleSimulationProcessor with ReverseClusterStrategyCaching with FixedGuessesStrategy {
      override def fixedGuesses: Seq[String] = Seq(startGuess)
    }
  }
  override def createFirstGuessOptimizer(): FirstGuessOptimizer = {
    new WordleFirstGuessOptimizer with ReverseClusterStrategy with WordleStandardWordSets
  }
}


trait WordleRandomGuessRunner extends WordleRunner with WordleStandardWordSets
  with InteractiveProcessorFactory with SimulationProcessFactory {

  override def createInteractiveProcessor(): InteractiveProcessor = {
    new WordleInteractiveProcessor with RandomGuessStrategy
  }
  override def createSimulationProcessor(): SimulationProcessor  = {
    new WordleSimulationProcessor with RandomGuessStrategy
  }
}


object WordleInteractiveStandardRunner extends App
  with XordleInteractiveRunner with WordleStandardRunner {
  runInteractive()
}

object WordleInteractiveAnswerOnlyRunner extends App
  with XordleInteractiveRunner with WordleAnswerOnlyRunner {
  runInteractive()
}

object WordleInteractiveReverseRunner extends App
  with XordleInteractiveRunner with WordleReverseRunner {
  runInteractive()
}

object WordleInteractiveRandomRunner extends App
  with XordleInteractiveRunner with WordleRandomGuessRunner {
  runInteractive()
}


object WordleSimulationStandardRunner extends App
  with XordleSimulationRunner with WordleStandardRunner {
  runSimulation()
}

object WordleSimulationAnswerOnlyRunner extends App
  with XordleSimulationRunner with WordleAnswerOnlyRunner {
  runSimulation()
}

object WordleSimulationCharFreqRunner extends App
  with XordleSimulationRunner with WordleCharFreqRunner {
  runSimulation()
}

object WordleSimulationReverseRunner extends App
  with XordleSimulationRunner with WordleReverseRunner {
  runSimulation()
}

object WordleSimulationRandomRunner extends App
  with XordleSimulationRunner with WordleRandomGuessRunner {
  runSimulation()
}


object WordleFirstGuessOptStandardRunner extends App
  with FirstGuessRunner with WordleStandardRunner {
  runOptimizer(12)
}

object WordleFirstGuessOptAnswerOnlyRunner extends App
  with FirstGuessRunner with WordleAnswerOnlyRunner {
  runOptimizer(12)
}

object WordleFirstGuessOptReverseRunner extends App
  with FirstGuessRunner  with WordleReverseRunner {
  runOptimizer(12)
}
