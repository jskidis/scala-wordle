package com.skidis.wordle
package nerdle

trait NerdleProcessor extends NerdleHintProps with NerdleGuessProps

trait NerdleInteractiveProcessor extends InteractiveProcessor
  with NerdleProcessor with NerdleInputValidator
