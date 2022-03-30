package com.skidis.wordle
package wordle.runner

import runners._
import strategy._

trait WordleRandomProcessor extends WordleProcessor
  with RandomGuessStrategy

trait WordleRandomInteractiveProcessor extends WordleRandomProcessor with InteractiveProcessor
trait WordleRandomSimulationProcessor extends WordleRandomProcessor with SimulationProcessor

trait WordleRandomRunner extends WordleRunner with WordleStandardWordSets
  with InteractiveProcessorFactory with SimulationProcessFactory {

  override def createInteractiveProcessor(): InteractiveProcessor = new WordleRandomInteractiveProcessor {}
  override def createSimulationProcessor(): SimulationProcessor = new WordleRandomSimulationProcessor {}
}