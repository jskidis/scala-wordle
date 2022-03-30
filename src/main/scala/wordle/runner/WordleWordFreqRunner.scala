package com.skidis.wordle
package wordle.runner

import runners._
import strategy._
import wordle._

trait WordleWordFreqProcessor extends WordleProcessor
  with WordFreqStrategy with FixedGuessesProvider {
  override def fixedGuesses: Seq[String] = Seq("SLATE")
}

trait WordleWordFreqInteractiveProcessor extends runner.WordleWordFreqProcessor with InteractiveProcessor with FixedGuessHardModeStrategy
trait WordleWordFreqSimulationProcessor extends runner.WordleWordFreqProcessor with SimulationProcessor with FixedGuessHardModeStrategy
trait WordleWordFreqOptimizer extends runner.WordleWordFreqProcessor with FirstGuessOptimizer

trait WordleWordFreqRunner extends WordleRunner with WordleStandardWordSets
  with InteractiveProcessorFactory with SimulationProcessFactory with FirstGuessOptFactory {

  override def createInteractiveProcessor(): InteractiveProcessor = new WordleWordFreqInteractiveProcessor {}
  override def createSimulationProcessor(): SimulationProcessor = new WordleWordFreqSimulationProcessor {}
  override def createFirstGuessOptimizer(): FirstGuessOptimizer = new WordleWordFreqOptimizer {}
}