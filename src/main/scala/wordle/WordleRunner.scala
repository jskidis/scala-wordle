package com.skidis.wordle
package wordle

import strategy._

import scala.io.Source

trait WordleRunner extends XrdleRunner {
  override def puzzleName: String = "Wordle"
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
    new WordleInteractiveProcessor with ClusterAndFreqStrategy with FixedGuessesStrategy {
      override def fixedGuesses: Seq[String] = Seq(firstGuess)
    }
  }
  override def createSimulationProcessor(startGuesses: Seq[String] = Nil): SimulationProcessor  = {
    new WordleSimulationProcessor with WordleProcessor with ClusterAndFreqStrategyCaching with FixedGuessesStrategy {
      override def fixedGuesses: Seq[String] = {
        if (startGuesses == Nil) Seq(firstGuess) else startGuesses
      }
    }
  }
  override def createFirstGuessOptimizer(): FirstGuessOptimizer = {
    new WordleFirstGuessOptimizer with ClusterAndFreqStrategy with WordleStandardWordSets
  }
}


trait WordleAnswerOnlyRunner extends WordleRunner with WordleAnswerOnlyWordSets
  with InteractiveProcessorFactory with SimulationProcessFactory with FirstGuessOptFactory {

  val firstGuess = "SLATE"

  override def createInteractiveProcessor(): InteractiveProcessor = {
    new WordleInteractiveProcessor with ClusterStrategy with FixedGuessesStrategy {
      override def fixedGuesses: Seq[String] = Seq(firstGuess)
    }
  }
  override def createSimulationProcessor(startGuesses: Seq[String] = Nil): SimulationProcessor  = {
    new WordleSimulationProcessor with WordleProcessor with ClusterStrategyCaching with FixedGuessesStrategy {
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

  val firstTwoGuesses = Seq("SLATE", "CORNY")

  override def createInteractiveProcessor(): InteractiveProcessor = {
    new WordleInteractiveProcessor with CharAndWordFreqStrategy with FixedGuessesStrategy {
      override def fixedGuesses: Seq[String] = firstTwoGuesses
    }
  }
  override def createSimulationProcessor(startGuesses: Seq[String] = Nil): SimulationProcessor  = {
    new WordleSimulationProcessor with CharAndWordFreqStrategy with FixedGuessesStrategy {
      override def fixedGuesses: Seq[String] = {
        if (startGuesses == Nil) firstTwoGuesses else startGuesses
      }
    }
  }
  override def createFirstGuessOptimizer(): FirstGuessOptimizer = {
    new WordleFirstGuessOptimizer with CharAndWordFreqStrategy with WordleStandardWordSets
  }
}

trait WordleWordFreqRunner extends WordleRunner with WordleAnswerOnlyWordSets
  with InteractiveProcessorFactory with SimulationProcessFactory with FirstGuessOptFactory {

  val firstGuess = "LEARN"

  override def createInteractiveProcessor(): InteractiveProcessor = {
    new WordleInteractiveProcessor with WordFreqStrategy with FixedGuessesStrategy {
      override def fixedGuesses: Seq[String] = Seq(firstGuess)
    }
  }
  override def createSimulationProcessor(startGuesses: Seq[String] = Nil): SimulationProcessor  = {
    new WordleSimulationProcessor with WordFreqStrategy with FixedGuessesStrategy {
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

  val firstGuess = "JAZZY"
  override lazy val answerSet: WordSet =  readWords(Source.fromResource("answers.txt")).take(100)

  override def createInteractiveProcessor(): InteractiveProcessor = {
    new WordleInteractiveProcessor with ReverseClusterStrategy with FixedGuessesStrategy {
      override def fixedGuesses: Seq[String] = Seq(firstGuess)
    }
  }
  override def createSimulationProcessor(startGuesses: Seq[String] = Nil): SimulationProcessor  = {
    new WordleSimulationProcessor with ReverseClusterStrategyCaching with FixedGuessesStrategy {
      override def fixedGuesses: Seq[String] = {
        if (startGuesses == Nil) Seq(firstGuess) else startGuesses
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


object WordleSimulationStandardRunner extends App
  with XrdleSimulationRunner with WordleStandardRunner {
  printResults(runSimulation())
}

object WordleSimulationAnswerOnlyRunner extends App
  with XrdleSimulationRunner with WordleAnswerOnlyRunner {
  printResults(runSimulation())
}

object WordleSimulationCharFreqRunner extends App
  with XrdleSimulationRunner with WordleCharFreqRunner {
  printResults(runSimulation())
}

object WordleSimulationWordFreqRunner extends App
  with XrdleSimulationRunner with WordleWordFreqRunner {
  printResults(runSimulation())
}

object WordleSimulationReverseRunner extends App
  with XrdleSimulationRunner with WordleReverseRunner {
  printResults(runSimulation())
}

object WordleSimulationRandomRunner extends App
  with XrdleSimulationRunner with WordleRandomGuessRunner {
  printResults(runSimulation())
}


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
