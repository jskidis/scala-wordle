package com.skidis.wordle

import scala.annotation.tailrec
import scala.io.StdIn

object ResultInput {
  val promptMsg = "Enter Results: "
  val errorMsg = s"\nInvalid results, results must be five characters and only contain (${validBlockChars.mkString(", ")})\n"

  @tailrec
  def generatePattern(reader: LineReader, writer: LineWriter, validator: Validator): ColorPattern = {
    writer(promptMsg)
    val input = reader().toUpperCase
    if (input.isEmpty) Nil
    else if (validator(input)) InputToColorsConversion.convert(input)
    else {
      writer(errorMsg)
      generatePattern(reader, writer, validator)
    }
  }

  // The second set of parameters is to align itn with the ColorPatternGenerator function signature
  //    re: (guess: String) => ColorPattern
  // The main app can pass translateInputToPatternCurry(reader, writer, validator) using default values
  //    as the *function* (i.e. currying) used to generate the color pattern for a guess
  //    even though this implement doesn't need the current guess (since it's derived from user input)
  def generatePatternCurryable(
    reader: LineReader = StdIn.readLine,
    writer: LineWriter = Console.print,
    validator: Validator = StandardResultValidator.validate)
    (guess: String = "")
  : ColorPattern = generatePattern(reader, writer, validator)
}
