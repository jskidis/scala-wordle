package com.skidis.wordle

import scala.annotation.tailrec

trait ResultInput extends LineReader with Writer with ResultValidator with InputToColorsConversion {
  def resultPrompt: String = "Enter Results: "

  @tailrec
  final def generatePattern(): ColorPattern = {
    writeString(resultPrompt)
    val input = readLine().toUpperCase
    if (input.isEmpty) Nil
    else if (validateResult(input)) convertInputToColors(input)
    else {
      writeLine(validationErrorMsg)
      generatePattern()
    }
  }
}
