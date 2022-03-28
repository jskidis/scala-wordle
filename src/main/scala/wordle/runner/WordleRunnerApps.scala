package com.skidis.wordle
package wordle.runner

import runners.{FirstGuessRunner, XrdleInteractiveRunner, XrdleSimulationRunner}

/* Interactive Runner Apps */
object WordleInteractiveStandardRunner extends App with XrdleInteractiveRunner with WordleStandardRunner {
  printWordleBlock(runInteractive(), maxGuesses)
}

object WordleInteractiveAnswerOnlyRunner extends App with XrdleInteractiveRunner with WordleAnswerOnlyRunner {
    printWordleBlock(runInteractive(), maxGuesses)
}

object WordleInteractiveCharFreqRunner extends App with XrdleInteractiveRunner with WordleCharFreqRunner {
  printWordleBlock(runInteractive(), maxGuesses)
}

object WordleInteractiveWordFreqRunner extends App with XrdleInteractiveRunner with WordleWordFreqRunner {
  printWordleBlock(runInteractive(), maxGuesses)
}

object WordleInteractiveReverseRunner extends App with XrdleInteractiveRunner with WordleReverseRunner {
  printWordleBlock(runInteractive(), maxGuesses)
}

object WordleInteractiveRandomRunner extends App with XrdleInteractiveRunner with WordleRandomRunner {
  printWordleBlock(runInteractive(), maxGuesses)
}

/* Simulation Runner Apps */
object WordleSimulationStandardRunner extends XrdleSimulationRunner with WordleStandardRunner with App {
  printResults(runSimulation())
}

object WordleSimulationAnswerOnlyRunner extends XrdleSimulationRunner with WordleAnswerOnlyRunner with App {
  printResults(runSimulation())
}

object WordleSimulationCharFreqRunner extends XrdleSimulationRunner with WordleCharFreqRunner with App {
  printResults(runSimulation())
}

object WordleSimulationWordFreqRunner extends XrdleSimulationRunner with WordleWordFreqRunner with App{
  printResults(runSimulation())
}

object WordleSimulationReverseRunner extends XrdleSimulationRunner with WordleReverseRunner with App {
  printResults(runSimulation())
}

/* First Guess(es) Optimizer Apps */
object FirstGuessSuggestion { val number = 12 }

object WordleFirstGuessOptStandardRunner extends App with FirstGuessRunner with WordleStandardRunner {
  runOptimizer(FirstGuessSuggestion.number)
}

object WordleFirstGuessOptAnswerOnlyRunner extends App with FirstGuessRunner with WordleAnswerOnlyRunner {
  runOptimizer(FirstGuessSuggestion.number)
}

object WordleFirstGuessOptCharFreqRunner extends App with FirstGuessRunner with WordleCharFreqRunner {
  runOptimizer(FirstGuessSuggestion.number)
}

object WordleFirstGuessOptWordFreqRunner extends App with FirstGuessRunner with WordleWordFreqRunner {
  runOptimizer(FirstGuessSuggestion.number)
}

object WordleFirstGuessOptReverseRunner extends App with FirstGuessRunner  with WordleReverseRunner {
  runOptimizer(FirstGuessSuggestion.number)
}

