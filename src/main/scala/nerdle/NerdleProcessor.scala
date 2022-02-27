package com.skidis.wordle
package nerdle

trait NerdleProcessor extends NerdleHintProps with NerdleGuessProps {
  def maxGuesses: Int = 6
}

trait NerdleInteractiveProcessor extends XordleInteractiveProcessor
  with NerdleProcessor with NerdleInputValidator
