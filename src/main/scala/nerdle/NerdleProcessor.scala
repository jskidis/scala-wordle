package com.skidis.wordle
package nerdle

import runners.{FirstGuessOptimizer, InteractiveProcessor, SimulationProcessor}

trait NerdleProcessor extends NerdleHintProps with NerdleGuessProps
trait MiniNerdleProcessor extends NerdleHintProps with MiniNerdleGuessProps

abstract class NerdleInteractiveProcessor extends InteractiveProcessor
  with NerdleProcessor with NerdleInputValidator

abstract class NerdleSimulationProcessor extends SimulationProcessor
  with NerdleProcessor

abstract class NerdleFirstGuessOptimizer extends FirstGuessOptimizer
  with NerdleProcessor

abstract class MiniNerdleInteractiveProcessor extends InteractiveProcessor
  with MiniNerdleProcessor with NerdleInputValidator

abstract class MiniNerdleSimulationProcessor extends SimulationProcessor
  with MiniNerdleProcessor

abstract class MiniNerdleFirstGuessOptimizer extends FirstGuessOptimizer
  with MiniNerdleProcessor

