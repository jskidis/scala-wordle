package com.skidis.wordle
package wordle

import runners._
import strategy._

import scala.io.Source

trait WordleRunner extends XrdleRunner {
  override def puzzleName: String = "Wordle"
  override def hardMode: Boolean = true
  val numFirstGuessSuggestions = 12
}

trait WordleAnswerSet extends WordReader {
  lazy val answerSet: WordSet = readWords(Source.fromResource("answers.txt"))
}

trait WordleStandardWordSets extends WordleAnswerSet with GuessAndAnswerSets with WordReader {
  override lazy val guessSet: WordSet = readWordFrequencies(Source.fromResource("word-frequency-filtered.txt"))
}

trait WordleAnswerOnlyWordSets extends WordleAnswerSet with GuessAndAnswerSets with WordReader {
  override lazy val guessSet: WordSet = readWordFrequencies(Source.fromResource("word-frequency-answers.txt"))
}

trait WordleStandardRunner extends WordleRunner with WordleStandardWordSets
  with InteractiveProcessorFactory with SimulationProcessFactory with FirstGuessOptFactory {

  val firstGuess = "SLATE"

  override def createInteractiveProcessor(): InteractiveProcessor = {
    new WordleInteractiveProcessor with ClusterAndWordFreqStrategy with FixedGuessHardModeStrategy {
      override def fixedGuesses: Seq[String] = Seq(firstGuess)
    }
  }
  override def createSimulationProcessor(startGuesses: Seq[String] = Nil): SimulationProcessor  = {
    new WordleSimulationProcessor with WordleProcessor with ClusterAndBothFreqStrategyCaching with FixedGuessHardModeStrategy {
      override def fixedGuesses: Seq[String] = {
        if (startGuesses == Nil) Seq(firstGuess) else startGuesses
      }
    }
  }
  override def createFirstGuessOptimizer(): FirstGuessOptimizer = {
    new WordleFirstGuessOptimizer with ClusterAndWordFreqStrategy with WordleStandardWordSets
  }
}


trait WordleAnswerOnlyRunner extends WordleRunner with WordleAnswerOnlyWordSets
  with InteractiveProcessorFactory with SimulationProcessFactory with FirstGuessOptFactory {

  val firstGuess = "SLATE"

  override def createInteractiveProcessor(): InteractiveProcessor = {
    new WordleInteractiveProcessor with ClusterStrategy with FixedGuessHardModeStrategy {
      override def fixedGuesses: Seq[String] = Seq(firstGuess)
    }
  }
  override def createSimulationProcessor(startGuesses: Seq[String] = Nil): SimulationProcessor  = {
    new WordleSimulationProcessor with WordleProcessor with ClusterStrategyCaching with FixedGuessHardModeStrategy {
      override def fixedGuesses: Seq[String] = {
        if (startGuesses == Nil) Seq(firstGuess) else startGuesses
      }
    }
  }
  override def createFirstGuessOptimizer(): FirstGuessOptimizer = {
    new WordleFirstGuessOptimizer with ClusterStrategy with WordleAnswerOnlyWordSets
  }
}


trait WordleCharFreqRunner extends WordleRunner with WordleStandardWordSets
  with InteractiveProcessorFactory with SimulationProcessFactory with FirstGuessOptFactory {

  val firstTwoGuesses = Seq("SLATE", "CRONY")
  override def hardMode: Boolean = false

  override def createInteractiveProcessor(): InteractiveProcessor = {
    new WordleInteractiveProcessor with CharAndWordFreqStrategy with FixedGuessWithThresholdStrategy {
      override def fixedGuesses: Seq[String] = firstTwoGuesses
      override def numRemainingWordsThreshold: Int = 36 // Available Guesses / Num Possible Color Patterns
    }
  }
  override def createSimulationProcessor(startGuesses: Seq[String] = Nil): SimulationProcessor  = {
    new WordleSimulationProcessor with CharAndWordFreqStrategy with FixedGuessWithThresholdStrategy {
      override def fixedGuesses: Seq[String] = {
        if (startGuesses == Nil) firstTwoGuesses else startGuesses
      }
      override def numRemainingWordsThreshold: Int = 36 // Available Guesses / Num Possible Color Patterns
    }
  }
  override def createFirstGuessOptimizer(): FirstGuessOptimizer = {
    new WordleFirstGuessOptimizer with CharAndWordFreqStrategy with WordleStandardWordSets
  }
}

trait WordleWordFreqRunner extends WordleRunner with WordleStandardWordSets
  with InteractiveProcessorFactory with SimulationProcessFactory with FirstGuessOptFactory {

  val firstGuess = "SLATE"

  override def createInteractiveProcessor(): InteractiveProcessor = {
    new WordleInteractiveProcessor with WordFreqStrategy with FixedGuessHardModeStrategy {
      override def fixedGuesses: Seq[String] = Seq(firstGuess)
    }
  }
  override def createSimulationProcessor(startGuesses: Seq[String] = Nil): SimulationProcessor  = {
    new WordleSimulationProcessor with WordFreqStrategy with FixedGuessHardModeStrategy {
      override def fixedGuesses: Seq[String] = {
        if (startGuesses == Nil) Seq(firstGuess) else startGuesses
      }
    }
  }
  override def createFirstGuessOptimizer(): FirstGuessOptimizer = {
    new WordleFirstGuessOptimizer with WordFreqStrategy with WordleStandardWordSets
  }
}


trait WordleReverseRunner extends WordleRunner with WordleStandardWordSets
  with InteractiveProcessorFactory with SimulationProcessFactory with FirstGuessOptFactory {

  val firstTwoGuesses = Seq("JAZZY", "QUEUE")
  override lazy val answerSet: WordSet =  readWords(Source.fromResource("answers.txt")).take(100)

  override def createInteractiveProcessor(): InteractiveProcessor = {
    new WordleInteractiveProcessor with ReverseClusterStrategy with FixedGuessHardModeStrategy {
      override def fixedGuesses: Seq[String] = firstTwoGuesses
    }
  }
  override def createSimulationProcessor(startGuesses: Seq[String] = Nil): SimulationProcessor  = {
    new WordleSimulationProcessor with ReverseClusterStrategyCaching with FixedGuessHardModeStrategy {
      override def fixedGuesses: Seq[String] = {
        if (startGuesses == Nil) firstTwoGuesses else startGuesses
      }
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
  override def createSimulationProcessor(startGuesses: Seq[String] = Nil): SimulationProcessor  = {
    new WordleSimulationProcessor with RandomGuessStrategy
  }
}


object WordleInteractiveStandardRunner extends App
  with XrdleInteractiveRunner with WordleStandardRunner {
  runInteractive()
}

object WordleInteractiveAnswerOnlyRunner extends App
  with XrdleInteractiveRunner with WordleAnswerOnlyRunner {
  runInteractive()
}

object WordleInteractiveCharFreqRunner extends App
  with XrdleInteractiveRunner with WordleCharFreqRunner {
  runInteractive()
}

object WordleInteractiveWordFreqRunner extends App
  with XrdleInteractiveRunner with WordleWordFreqRunner {
  runInteractive()
}

object WordleInteractiveReverseRunner extends App
  with XrdleInteractiveRunner with WordleReverseRunner {
  runInteractive()
}

object WordleInteractiveRandomRunner extends App
  with XrdleInteractiveRunner with WordleRandomGuessRunner {
  runInteractive()
}


trait WordleSimulationStandardRunner extends XrdleSimulationRunner with WordleStandardRunner {
  def main(args: Array[String]):Unit = printResults(runSimulation())
}
object WordleSimulationStandardRunner extends WordleSimulationStandardRunner

trait WordleSimulationAnswerOnlyRunner extends XrdleSimulationRunner with WordleAnswerOnlyRunner {
  def main(args: Array[String]):Unit = printResults(runSimulation())
}
object WordleSimulationAnswerOnlyRunner extends WordleSimulationAnswerOnlyRunner

trait WordleSimulationCharFreqRunner extends XrdleSimulationRunner with WordleCharFreqRunner {
  def main(args: Array[String]):Unit = printResults(runSimulation())
}
object WordleSimulationCharFreqRunner extends WordleSimulationCharFreqRunner

trait WordleSimulationWordFreqRunner extends XrdleSimulationRunner with WordleWordFreqRunner {
  def main(args: Array[String]):Unit = printResults(runSimulation())
}
object WordleSimulationWordFreqRunner extends WordleSimulationWordFreqRunner

trait WordleSimulationReverseRunner extends XrdleSimulationRunner with WordleReverseRunner {
def main(args: Array[String]):Unit = printResults(runSimulation())
}
object WordleSimulationReverseRunner extends WordleSimulationReverseRunner


object WordleFirstGuessOptStandardRunner extends App
  with FirstGuessRunner with WordleStandardRunner {
  runOptimizer(numFirstGuessSuggestions)
}

object WordleFirstGuessOptAnswerOnlyRunner extends App
  with FirstGuessRunner with WordleAnswerOnlyRunner {
  runOptimizer(numFirstGuessSuggestions)
}

object WordleFirstGuessOptCharFreqRunner extends App
  with FirstGuessRunner with WordleCharFreqRunner {
  runOptimizer(numFirstGuessSuggestions)
}

object WordleFirstGuessOptWordFreqRunner extends App
  with FirstGuessRunner with WordleWordFreqRunner {
  runOptimizer(numFirstGuessSuggestions)
}

object WordleFirstGuessOptReverseRunner extends App
  with FirstGuessRunner  with WordleReverseRunner {
  runOptimizer(numFirstGuessSuggestions)
}
