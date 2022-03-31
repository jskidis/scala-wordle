package com.skidis.wordle
package wordle.runner

import hintgen.CachingWordHintsGenerator
import runners._
import strategy._

import scala.io.Source

trait WordleReverseProcessor extends WordleProcessor
  with ClusterStrategy with ReverseScoringStrategy with FixedGuessesProvider {
  override def fixedGuesses: Seq[String] = Seq("JAZZY", "QUEUE")
}

trait WordleReverseInteractiveProcessor extends WordleReverseProcessor with InteractiveProcessor with FixedGuessHardModeStrategy
trait WordleReverseSimulationProcessor extends WordleReverseProcessor with SimulationProcessor with FixedGuessHardModeStrategy with CachingWordHintsGenerator
trait WordleReverseOptimizer extends WordleReverseProcessor with FirstGuessOptimizer

trait WordleReverseRunner extends WordleRunner with WordleStandardWordSets
  with InteractiveProcessorFactory with SimulationProcessFactory with FirstGuessOptFactory {

  override lazy val answerSet: WordSet =  readWords(Source.fromResource("answers.txt")).take(231)
  override lazy val guessSet: WordSet = readWords(Source.fromResource("guessable-words.txt"))

  override def createInteractiveProcessor(): InteractiveProcessor = new WordleReverseInteractiveProcessor {}
  override def createSimulationProcessor(): SimulationProcessor = new WordleReverseSimulationProcessor {}
  override def createFirstGuessOptimizer(): FirstGuessOptimizer = new WordleReverseOptimizer {}
}