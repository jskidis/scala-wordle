package com.skidis.wordle
package wordle.runner

import hintgen.CachingWordHintsGenerator
import runners.{InteractiveProcessor, InteractiveProcessorFactory, SimulationProcessFactory, SimulationProcessor}
import strategy._

// This is basically just a collection of runners that use some of less optimal strategies combinations
// I've added them here so that I can use them in the "Wordle of the Day" integration test
// just to see how they'd do on a particular day when I'm running the rest of the runners

trait WordleClusterOnlyProcessor extends WordleProcessor
  with ClusterStrategy with FixedGuessesProvider with FixedGuessHardModeStrategy {
  override def fixedGuesses: Seq[String] = Seq("SLATE")
}

trait WordleClusterOnlyInteractiveProcessor extends WordleClusterOnlyProcessor with InteractiveProcessor
trait WordleClusterOnlySimulationProcessor extends WordleClusterOnlyProcessor with SimulationProcessor with CachingWordHintsGenerator

trait WordleClusterOnlyRunner extends WordleRunner with WordleStandardWordSets
  with InteractiveProcessorFactory with SimulationProcessFactory {
  override def createInteractiveProcessor(): InteractiveProcessor = new WordleClusterOnlyInteractiveProcessor {}
  override def createSimulationProcessor(): SimulationProcessor = new WordleClusterOnlySimulationProcessor {}

}


trait WordleCharFreqOnlyProcessor extends WordleProcessor
  with CharFreqStrategy with FixedGuessesProvider with FixedGuessWithThresholdStrategy {
    override def fixedGuesses: Seq[String] = Seq("SLATE", "CRONY")
    override def numRemainingWordsThreshold: Int = 36
}

trait WordleCharFreqOnlyInteractiveProcessor extends WordleCharFreqOnlyProcessor with InteractiveProcessor
trait WordleCharFreqOnlySimulationProcessor extends WordleCharFreqOnlyProcessor with SimulationProcessor

trait WordleCharFreqOnlyRunner extends WordleRunner with WordleStandardWordSets
  with InteractiveProcessorFactory with SimulationProcessFactory {
  override def hardMode: Boolean = false
  override def createInteractiveProcessor(): InteractiveProcessor = new WordleCharFreqOnlyInteractiveProcessor {}
  override def createSimulationProcessor(): SimulationProcessor = new WordleCharFreqOnlySimulationProcessor {}
}


trait WordleClusterAndCharFreqProcessor extends WordleProcessor
  with ClusterAndCharFreqStrategy with FixedGuessesProvider with FixedGuessWithThresholdStrategy {
    override def fixedGuesses: Seq[String] = Seq("SLATE", "CRONY")
    override def numRemainingWordsThreshold: Int = 36
}

trait WordleClusterAndCharFreqInteractiveProcessor extends WordleClusterAndCharFreqProcessor with InteractiveProcessor
trait WordleClusterAndCharFreqSimulationProcessor extends WordleClusterAndCharFreqProcessor with SimulationProcessor with CachingWordHintsGenerator

trait WordleClusterAndCharFreqRunner extends WordleRunner with WordleStandardWordSets
  with InteractiveProcessorFactory with SimulationProcessFactory {
  override def hardMode: Boolean = false
  override def createInteractiveProcessor(): InteractiveProcessor = new WordleClusterAndCharFreqInteractiveProcessor {}
  override def createSimulationProcessor(): SimulationProcessor = new WordleClusterAndCharFreqSimulationProcessor {}
}


trait WordleClusterAndBothFreqProcessor extends WordleProcessor
  with ClusterAndBothFreqStrategy with FixedGuessesProvider with FixedGuessWithThresholdStrategy {
  override def fixedGuesses: Seq[String] = Seq("SLATE", "CRONY")
  override def numRemainingWordsThreshold: Int = 36
}

trait WordleClusterAndBothFreqInteractiveProcessor extends WordleClusterAndBothFreqProcessor with InteractiveProcessor
trait WordleClusterAndBothFreqSimulationProcessor extends WordleClusterAndBothFreqProcessor with SimulationProcessor

trait WordleClusterAndBothFreqRunner extends WordleRunner with WordleStandardWordSets
  with InteractiveProcessorFactory with SimulationProcessFactory {
  override def hardMode: Boolean = false
  override def createInteractiveProcessor(): InteractiveProcessor = new WordleClusterAndBothFreqInteractiveProcessor {}
  override def createSimulationProcessor(): SimulationProcessor = new WordleClusterAndBothFreqSimulationProcessor {}
}
