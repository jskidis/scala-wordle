package com.skidis.wordle
package wordle.runner

import hintgen.CachingWordHintsGenerator
import runners._
import strategy._

trait WordleStandardProcessor extends WordleProcessor with WordleStandardWordSets
  with ClusterAndWordFreqStrategy with FixedGuessesProvider {
  override def fixedGuesses: Seq[String] = Seq("SLATE")
}

trait WordleStandardInteractiveProcessor extends WordleStandardProcessor with InteractiveProcessor with FixedGuessHardModeStrategy
trait WordleStandardSimulationProcessor extends WordleStandardProcessor with SimulationProcessor with FixedGuessHardModeStrategy with CachingWordHintsGenerator
trait WordleStandardOptimizer extends WordleStandardProcessor with FirstGuessOptimizer

trait WordleStandardRunner extends WordleRunner with WordleStandardWordSets
  with InteractiveProcessorFactory with SimulationProcessFactory with FirstGuessOptFactory {

  override def createInteractiveProcessor(): InteractiveProcessor = new WordleStandardInteractiveProcessor {}
  override def createSimulationProcessor(): SimulationProcessor = new WordleStandardSimulationProcessor {}
  override def createFirstGuessOptimizer(): FirstGuessOptimizer = new WordleStandardOptimizer {}
}