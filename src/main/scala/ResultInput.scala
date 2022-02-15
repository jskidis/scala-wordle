package com.skidis.wordle

import BlockColor.BlockColor

import scala.annotation.tailrec
import scala.io.StdIn

object ResultInput {
  val PromptMsg = "Enter Results: "
  val ErrorMsg = s"\nInvalid results, results must be five characters and only contain (${validChars.mkString(", ")})\n"

  @tailrec
  def generatePattern(reader: LineReader, writer: LineWriter, validator: Validator): List[BlockColor] = {
    writer(PromptMsg)
    val input = reader().toUpperCase
    if (input.isEmpty) Nil
    else if (validator(input)) InputToColorsConversion.convert(input)
    else {
      writer(ErrorMsg)
      generatePattern(reader, writer, validator)
    }
  }

  // The second set of parameters is to align itn with the ColorPatternGenerator function signature
  //    re: (guess: String) => List[BlockColor]
  // The main app can pass translateInputToPatternCurry(reader, writer, validator) using default values
  //    as the *function* (i.e. currying) used to generate the color pattern for a guess
  //    even though this implement doesn't need the current guess (since it's derived from user input)
  def generatePatternCurryable(
      reader: LineReader = StdIn.readLine,
      writer: LineWriter = Console.print,
      validator: Validator = InputValidator.validate)
                              (guess: String = "")
  : List[BlockColor] = {
    generatePattern(reader, writer, validator)
  }
}
