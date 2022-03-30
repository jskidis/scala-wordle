package com.skidis.wordle
package nerdle.runner

import runners._
import strategy._

trait NerdleRandomProcessor extends NerdleProcessor with RandomGuessStrategy
trait NerdleRandomInteractiveProcessor extends NerdleRandomProcessor with InteractiveProcessor
trait NerdleRandomSimulationProcessor extends NerdleRandomProcessor with SimulationProcessor

trait NerdleRandomGuessRunner extends NerdleRunner with NerdleStandardWordSets
  with InteractiveProcessorFactory with SimulationProcessFactory {

  override def createInteractiveProcessor(): InteractiveProcessor = new NerdleRandomInteractiveProcessor {}
  override def createSimulationProcessor(): SimulationProcessor = new NerdleRandomSimulationProcessor {}
}
