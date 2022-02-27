package com.skidis.wordle
package wordle

import strategy.{ClusterStrategy, ReverseClusterStrategy}

import scala.io.Source

trait WordleRunner extends XordleRunner with WordReader {
  override def puzzleName: String = "Wordle"
  override lazy val answerSet: WordSet = readWords(Source.fromResource("answers.txt"))
}

trait WordleStandardRunner extends WordleRunner {
  override def startGuess: String = "SLATE"
  override lazy val guessSet: WordSet = readWordFrequencies(Source.fromResource("word-frequency-filtered.txt"))

  override def createInteractiveProcessor(): InteractiveProcessor = {
    new WordleInteractiveProcessor with ClusterStrategy
  }
  override def createSimulationProcessor(answer: String): SimulationProcessor  = {
    new SimulationProcessor(answer) with WordleProcessor with ClusterStrategy
  }
}

trait WordleAnswerOnlyRunner extends WordleStandardRunner {
  override lazy val guessSet: WordSet = readWords(Source.fromResource("answers.txt"))
}

trait WordleReverseRunner extends WordleRunner with WordReader {
  override def startGuess: String = "JAZZY"
  override lazy val guessSet: WordSet = readWordFrequencies(Source.fromResource("words-filtered-by-frequency.txt"))
  override lazy val answerSet: WordSet =  readWords(Source.fromResource("answers.txt")).take(100)

  override def createInteractiveProcessor(): InteractiveProcessor = {
    new WordleInteractiveProcessor with ReverseClusterStrategy
  }
  override def createSimulationProcessor(answer: String): SimulationProcessor  = {
    new SimulationProcessor(answer) with WordleProcessor with ReverseClusterStrategy
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