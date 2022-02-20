package com.skidis.wordle

import scala.annotation.tailrec

trait ResultInput extends InputToColorsConversion {
  val promptMsg = "Enter Results: "
  val errorMsg = s"\nInvalid results, results must be five characters and only contain (${validBlockChars.mkString(", ")})\n"

  @tailrec
  final def generatePattern(reader: LineReader, writer: LineWriter, validator: Validator): ColorPattern = {
    writer(promptMsg)
    val input = reader().toUpperCase
    if (input.isEmpty) Nil
    else if (validator(input)) convertInputToColors(input)
    else {
      writer(errorMsg)
      generatePattern(reader, writer, validator)
    }
  }
}

object ResultInput extends ResultInput
