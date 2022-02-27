package com.skidis.wordle
package wordle

import input.BasicResultAndGuessValidator

trait WordleProcessor extends WordleHintProps with WordleGuessProps {
  def maxGuesses: Int = 6
}

trait WordleInteractiveProcessor extends XordleInteractiveProcessor
  with WordleProcessor with BasicResultAndGuessValidator
