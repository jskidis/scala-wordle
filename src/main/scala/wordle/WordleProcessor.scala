package com.skidis.wordle
package wordle

import input.BasicResultAndGuessValidator
import runners.{FirstGuessOptimizer, InteractiveProcessor, SimulationProcessor}

trait WordleProcessor extends WordleHintProps with WordleGuessProps

abstract class WordleInteractiveProcessor extends InteractiveProcessor
  with WordleProcessor with BasicResultAndGuessValidator

abstract class WordleSimulationProcessor extends SimulationProcessor
  with WordleProcessor

abstract class WordleFirstGuessOptimizer extends FirstGuessOptimizer
  with WordleProcessor
