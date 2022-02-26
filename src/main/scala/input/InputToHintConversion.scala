package com.skidis.wordle
package input

trait InputToHintConversion {
  def convertInputToHints(input: String, hintProps: HintProps): WordHints = {
    input.map { ch =>
      if (ch.toUpper == hintProps.inPosHint.inputChar.toUpper) hintProps.inPosHint
      else if (ch.toUpper == hintProps.inWordHint.inputChar.toUpper) hintProps.inWordHint
      else hintProps.missHint
    }
  }
}

object InputToHintConversion extends InputToHintConversion