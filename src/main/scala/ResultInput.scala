package com.skidis.wordle

import scala.annotation.tailrec

trait ResultInput extends LineReader with Writer with ResultValidator with InputToColorsConversion {
  val resultPrompt = "Enter Results: "
  val resultErrorMsg = s"Invalid results, results must be five characters and only contain (${validBlockChars.mkString(", ")})"

  @tailrec
  final def generatePattern(): ColorPattern = {
    writeString(resultPrompt)
    val input = readLine().toUpperCase
    if (input.isEmpty) Nil
    else if (validateResult(input)) convertInputToColors(input)
    else {
      writeLine(resultErrorMsg)
      generatePattern()
    }
  }
}
