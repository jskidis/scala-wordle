package com.skidis.wordle

import scala.annotation.tailrec

trait ResultInput extends LineReader with LineWriter with ResultValidator with InputToColorsConversion {
  val resultPrompt = "Enter Results: "
  val resultErrorMsg = s"\nInvalid results, results must be five characters and only contain (${validBlockChars.mkString(", ")})\n"

  @tailrec
  final def generatePattern(): ColorPattern = {
    writeLine(resultPrompt)
    val input = readLine().toUpperCase
    if (input.isEmpty) Nil
    else if (validateResult(input)) convertInputToColors(input)
    else {
      writeLine(resultErrorMsg)
      generatePattern()
    }
  }
}
