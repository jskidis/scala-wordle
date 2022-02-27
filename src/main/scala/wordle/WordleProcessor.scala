package com.skidis.wordle
package wordle

import input.BasicResultAndGuessValidator

trait WordleProcessor extends WordleHintProps with WordleGuessProps

trait WordleInteractiveProcessor extends InteractiveProcessor
  with WordleProcessor with BasicResultAndGuessValidator
