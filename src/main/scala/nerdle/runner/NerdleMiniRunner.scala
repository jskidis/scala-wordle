package com.skidis.wordle
package nerdle.runner

import hintgen.CachingWordHintsGenerator
import runners._
import strategy._

trait NerdleMiniProcessor extends NerdleProcessorMiniProps
  with ClusterAndCharFreqStrategy with FixedGuessesProvider {
  override def fixedGuesses: Seq[String] = Seq("28/7=4")
}

trait NerdleMiniInteractiveProcessor extends NerdleMiniProcessor with InteractiveProcessor with FixedGuessesStrategy
trait NerdleMiniSimulationProcessor extends NerdleMiniProcessor with SimulationProcessor with CachingWordHintsGenerator with FixedGuessesStrategy
trait NerdleMiniOptimizer extends NerdleMiniProcessor with FirstGuessOptimizer

trait NerdleMiniRunner extends NerdleRunner with NerdleMiniWordSets
  with InteractiveProcessorFactory with SimulationProcessFactory with FirstGuessOptFactory {

  override def puzzleName: String = "mini nerdlegame"
  override def createInteractiveProcessor(): InteractiveProcessor = new NerdleMiniInteractiveProcessor {}
  override def createSimulationProcessor(): SimulationProcessor  = new NerdleMiniSimulationProcessor {}
  override def createFirstGuessOptimizer(): FirstGuessOptimizer = new NerdleMiniOptimizer {}
}
