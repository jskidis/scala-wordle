package com.skidis.wordle
package input

trait InputToColorsConversion {
  def convertInputToColors(input: String, hintProps: HintProps): WordHints = {
    input.map { ch =>
      if (ch.toUpper == hintProps.inPosHint.inputChar.toUpper) hintProps.inPosHint
      else if (ch.toUpper == hintProps.inWordHint.inputChar.toUpper) hintProps.inWordHint
      else hintProps.missHint
    }.toList
  }
}

object InputToColorsConversion extends InputToColorsConversion