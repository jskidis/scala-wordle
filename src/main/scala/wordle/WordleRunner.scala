package com.skidis.wordle
package wordle

import strategy.{ClusterAndFreqStrategy, ClusterStrategy, ReverseClusterStrategy}

import scala.io.Source

trait WordleRunner extends XordleRunner {
  override def puzzleName: String = "Wordle"
}


trait WordleStandardWordSets extends GuessAndAnswerSets with WordReader {
  override lazy val guessSet: WordSet = readWordFrequencies(Source.fromResource("word-frequency-filtered.txt"))
  override lazy val answerSet: WordSet = readWords(Source.fromResource("answers.txt"))
}

trait WordleStandardRunner extends WordleRunner with WordleStandardWordSets {
  override def startGuess: String = "SLATE"

  override def createInteractiveProcessor(): InteractiveProcessor = {
    new WordleInteractiveProcessor with ClusterAndFreqStrategy
  }
  override def createSimulationProcessor(answer: String): SimulationProcessor  = {
    new WordleSimulationProcessor(answer) with WordleProcessor with ClusterAndFreqStrategy
  }
  override def createFirstGuessOptimizer(): FirstGuessOptimizator = {
    new WordleFirstGuessOptimizer with ClusterAndFreqStrategy with WordleStandardWordSets
  }
}


trait WordleAnswerOnlyWordSets extends GuessAndAnswerSets with WordReader {
  override lazy val guessSet: WordSet = readWords(Source.fromResource("answers.txt"))
  override lazy val answerSet: WordSet = readWords(Source.fromResource("answers.txt"))
}

trait WordleAnswerOnlyRunner extends WordleRunner with WordleAnswerOnlyWordSets {
  override def startGuess: String = "SLATE"

  override def createInteractiveProcessor(): InteractiveProcessor = {
    new WordleInteractiveProcessor with ClusterStrategy
  }
  override def createSimulationProcessor(answer: String): SimulationProcessor  = {
    new WordleSimulationProcessor(answer) with WordleProcessor with ClusterStrategy
  }
  override def createFirstGuessOptimizer(): FirstGuessOptimizator = {
    new WordleFirstGuessOptimizer with ClusterStrategy with WordleAnswerOnlyWordSets
  }
}


trait WordleReverseWordSets extends GuessAndAnswerSets with WordReader {
  override lazy val guessSet: WordSet = readWordFrequencies(Source.fromResource("words-filtered-by-frequency.txt"))
  override lazy val answerSet: WordSet =  readWords(Source.fromResource("answers.txt")).take(100)
}

trait WordleReverseRunner extends WordleRunner with WordleReverseWordSets {
  override def startGuess: String = "JAZZY"

  override def createInteractiveProcessor(): InteractiveProcessor = {
    new WordleInteractiveProcessor with ReverseClusterStrategy
  }
  override def createSimulationProcessor(answer: String): SimulationProcessor  = {
    new WordleSimulationProcessor(answer) with ReverseClusterStrategy
  }
  override def createFirstGuessOptimizer(): FirstGuessOptimizator = {
    new WordleFirstGuessOptimizer with ReverseClusterStrategy with WordleReverseWordSets
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