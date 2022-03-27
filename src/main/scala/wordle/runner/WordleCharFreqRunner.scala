package com.skidis.wordle
package wordle.runner

import runners._
import strategy._

trait WordleCharFreqProcessor extends WordleProcessor with WordleStandardWordSets
  with CharAndWordFreqStrategy with FixedGuessesProvider {
  override def fixedGuesses: Seq[String] = Seq("SLATE", "CRONY")
}

trait WordleCharFreqInteractiveProcessor extends WordleCharFreqProcessor with InteractiveProcessor
  with FixedGuessWithThresholdStrategy {
  override def numRemainingWordsThreshold: Int = 36
}

trait WordleCharFreqSimulationProcessor extends WordleCharFreqProcessor with SimulationProcessor
  with FixedGuessWithThresholdStrategy {
  override def numRemainingWordsThreshold: Int = 36
}

trait WordleCharFreqOptimizer extends WordleCharFreqProcessor with FirstGuessOptimizer

trait WordleCharFreqRunner extends WordleRunner with WordleStandardWordSets
  with InteractiveProcessorFactory with SimulationProcessFactory with FirstGuessOptFactory {

  override def createInteractiveProcessor(): InteractiveProcessor = new WordleCharFreqInteractiveProcessor {}
  override def createSimulationProcessor(): SimulationProcessor = new WordleCharFreqSimulationProcessor {}
  override def createFirstGuessOptimizer(): FirstGuessOptimizer = new WordleCharFreqOptimizer {}
}