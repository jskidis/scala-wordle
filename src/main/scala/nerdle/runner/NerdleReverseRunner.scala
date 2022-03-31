package com.skidis.wordle
package nerdle.runner

import hintgen.CachingWordHintsGenerator
import runners._
import strategy._

trait NerdleReverseProcessor extends NerdleProcessor
  with ClusterStrategy with ReverseScoringStrategy with FixedGuessesProvider {
  override def fixedGuesses: Seq[String] = Seq("9+9+1=19")
}

trait NerdleReverseInteractiveProcessor extends NerdleReverseProcessor with InteractiveProcessor with FixedGuessesStrategy
trait NerdleReverseSimulationProcessor extends NerdleReverseProcessor with SimulationProcessor with FixedGuessesStrategy with CachingWordHintsGenerator
trait NerdleReverseOptimizer extends NerdleReverseProcessor with FirstGuessOptimizer


trait NerdleReverseRunner extends NerdleRunner with NerdleStandardWordSets
  with InteractiveProcessorFactory with SimulationProcessFactory with FirstGuessOptFactory {

  override lazy val answerSet: WordSet =  generate8CharEquations().take(100)


  override def createInteractiveProcessor(): InteractiveProcessor = new NerdleReverseInteractiveProcessor {}
  override def createSimulationProcessor(): SimulationProcessor = new NerdleReverseSimulationProcessor {}
  override def createFirstGuessOptimizer(): FirstGuessOptimizer = new NerdleReverseOptimizer {}
}
