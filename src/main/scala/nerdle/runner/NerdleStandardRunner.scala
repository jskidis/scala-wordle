package com.skidis.wordle
package nerdle.runner

import hintgen.CachingWordHintsGenerator
import runners._
import strategy._

trait NerdleStandardProcessor extends NerdleProcessor
  with ClusterAndCharFreqStrategy with FixedGuessesProvider {
  override def fixedGuesses: Seq[String] = Seq("54-38=16")
}

trait NerdleStandardInteractiveProcessor extends NerdleStandardProcessor with InteractiveProcessor with FixedGuessesStrategy
trait NerdleStandardSimulationProcessor extends NerdleStandardProcessor with SimulationProcessor with FixedGuessesStrategy with CachingWordHintsGenerator
trait NerdleStandardOptimizer extends NerdleStandardProcessor with FirstGuessOptimizer

trait NerdleStandardRunner extends NerdleRunner with NerdleStandardWordSets
  with InteractiveProcessorFactory with SimulationProcessFactory with FirstGuessOptFactory {

  override def createInteractiveProcessor(): InteractiveProcessor = new NerdleStandardInteractiveProcessor {}
  override def createSimulationProcessor(): SimulationProcessor = new NerdleStandardSimulationProcessor {}
  override def createFirstGuessOptimizer(): FirstGuessOptimizer = new NerdleStandardOptimizer {}
}
