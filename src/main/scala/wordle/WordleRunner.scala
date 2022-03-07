package com.skidis.wordle
package wordle

import strategy._

import scala.io.Source
import scala.util.Random

trait WordleRunner extends XordleRunner {
  override def puzzleName: String = "Wordle"
}

trait WordleAnswerSet extends WordReader {
  lazy val answerSet: WordSet = readWords(Source.fromResource("answers.txt"))
}

trait WordleStandardWordSets extends WordleAnswerSet with GuessAndAnswerSets with WordReader {
  override lazy val guessSet: WordSet = readWordFrequencies(Source.fromResource("word-frequency-filtered.txt"))
}

trait WordleStandardRunner extends WordleRunner with WordleStandardWordSets {
  override def startGuess: String = "SLATE"

  override def createInteractiveProcessor(): InteractiveProcessor = {
    new WordleInteractiveProcessor with ClusterAndFreqStrategy
  }
  override def createSimulationProcessor(): SimulationProcessor  = {
    new WordleSimulationProcessor with WordleProcessor with ClusterAndFreqStrategyCaching
  }
  override def createFirstGuessOptimizer(): FirstGuessOptimizer = {
    new WordleFirstGuessOptimizer with ClusterAndFreqStrategy with WordleStandardWordSets
  }
}


trait WordleAnswerOnlyWordSets extends WordleAnswerSet with GuessAndAnswerSets with WordReader {
  override lazy val guessSet: WordSet = readWords(Source.fromResource("answers.txt"))
}

trait WordleAnswerOnlyRunner extends WordleRunner with WordleAnswerOnlyWordSets {
  override def startGuess: String = "SLATE"

  override def createInteractiveProcessor(): InteractiveProcessor = {
    new WordleInteractiveProcessor with ClusterStrategy
  }
  override def createSimulationProcessor(): SimulationProcessor  = {
    new WordleSimulationProcessor with WordleProcessor with ClusterStrategyCaching
  }
  override def createFirstGuessOptimizer(): FirstGuessOptimizer = {
    new WordleFirstGuessOptimizer with ClusterStrategy with WordleAnswerOnlyWordSets
  }
}


trait WordleReverseWordSets extends GuessAndAnswerSets with WordReader {
  override lazy val guessSet: WordSet = readWordFrequencies(Source.fromResource("word-frequency-filtered.txt"))
  override lazy val answerSet: WordSet =  readWords(Source.fromResource("answers.txt")).take(100)
}

trait WordleReverseRunner extends WordleRunner with WordleReverseWordSets {
  override def startGuess: String = "JAZZY"

  override def createInteractiveProcessor(): InteractiveProcessor = {
    new WordleInteractiveProcessor with ReverseClusterStrategy
  }
  override def createSimulationProcessor(): SimulationProcessor  = {
    new WordleSimulationProcessor with ReverseClusterStrategyCaching
  }
  override def createFirstGuessOptimizer(): FirstGuessOptimizer = {
    new WordleFirstGuessOptimizer with ReverseClusterStrategy with WordleReverseWordSets
  }
}


trait WordleRandomGuessWordSets extends WordleAnswerSet with GuessAndAnswerSets with WordReader {
  override lazy val guessSet: WordSet = readWordFrequencies(Source.fromResource("word-frequency-filtered.txt"))
}

trait WordleRandomGuessRunner extends WordleRunner with WordleRandomGuessWordSets {
  override def startGuess: String = {
    Random.shuffle(guessSet.toVector).headOption.map(_.phrase).getOrElse("WORDS")
  }

  override def createInteractiveProcessor(): InteractiveProcessor = {
    new WordleInteractiveProcessor with RandomGuessStrategy
  }
  override def createSimulationProcessor(): SimulationProcessor  = {
    new WordleSimulationProcessor with RandomGuessStrategy
  }
  override def createFirstGuessOptimizer(): FirstGuessOptimizer = {
    new WordleFirstGuessOptimizer with RandomGuessStrategy with WordleReverseWordSets
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
