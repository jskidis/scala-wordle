package com.skidis.wordle
package input

trait InputToHintConversion extends HintProps {
  def convertInputToHints(input: String): WordHints = {
    input.map { ch =>
      if (ch.toUpper == inPosHint.inputChar.toUpper) inPosHint
      else if (ch.toUpper == inWordHint.inputChar.toUpper) inWordHint
      else missHint
    }
  }
}
