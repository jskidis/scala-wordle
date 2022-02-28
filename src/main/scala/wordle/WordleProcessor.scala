package com.skidis.wordle
package wordle

import input.BasicResultAndGuessValidator

trait WordleProcessor extends WordleHintProps with WordleGuessProps

abstract class WordleInteractiveProcessor extends InteractiveProcessor
  with WordleProcessor with BasicResultAndGuessValidator

abstract class WordleSimulationProcessor(answer: String ) extends SimulationProcessor(answer)
  with WordleProcessor

abstract class WordleFirstGuessOptimizer extends FirstGuessOptimizator
  with WordleProcessor
