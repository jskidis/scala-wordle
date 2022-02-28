package com.skidis.wordle
package nerdle

trait NerdleProcessor extends NerdleHintProps with NerdleGuessProps
trait MiniNerdleProcessor extends NerdleHintProps with MiniNerdleGuessProps

abstract class NerdleInteractiveProcessor extends InteractiveProcessor
  with NerdleProcessor with NerdleInputValidator

abstract class NerdleSimulationProcessor(answer: String) extends SimulationProcessor(answer)
  with NerdleProcessor

abstract class NerdleFirstGuessOptimizer extends FirstGuessOptimizer
  with NerdleProcessor

abstract class MiniNerdleInteractiveProcessor extends InteractiveProcessor
  with MiniNerdleProcessor with NerdleInputValidator

abstract class MiniNerdleSimulationProcessor(answer: String) extends SimulationProcessor(answer)
  with MiniNerdleProcessor

abstract class MiniNerdleFirstGuessOptimizer extends FirstGuessOptimizer
  with MiniNerdleProcessor

