package com.skidis.wordle
package wordle.runner

import hintgen.CachingWordHintsGenerator
import runners._
import strategy._

trait WordleAnswerOnlyProcessor extends WordleProcessor with WordleAnswerOnlyWordSets
  with ClusterStrategy with FixedGuessesProvider {
  override def fixedGuesses: Seq[String] = Seq("SLATE")
}

trait WordleAnswerOnlyInteractiveProcessor extends WordleAnswerOnlyProcessor with InteractiveProcessor with FixedGuessHardModeStrategy
trait WordleAnswerOnlySimulationProcessor extends WordleAnswerOnlyProcessor with SimulationProcessor with FixedGuessHardModeStrategy with CachingWordHintsGenerator
trait WordleAnswerOnlyOptimizer extends WordleAnswerOnlyProcessor with FirstGuessOptimizer

trait WordleAnswerOnlyRunner extends WordleRunner with WordleAnswerOnlyWordSets
  with InteractiveProcessorFactory with SimulationProcessFactory with FirstGuessOptFactory {

  override def createInteractiveProcessor(): InteractiveProcessor = new WordleAnswerOnlyInteractiveProcessor {}
  override def createSimulationProcessor(): SimulationProcessor = new WordleAnswerOnlySimulationProcessor {}
  override def createFirstGuessOptimizer(): FirstGuessOptimizer = new WordleAnswerOnlyOptimizer {}
}