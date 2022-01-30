package com.skidis.wordle

import BlockColor.BlockColor

object GatherResult {
  type LineReader = () => String
  type LineWriter = String => Unit
  type Validator = String => Boolean

  val PromptMsg = "Enter Results: "
  val ErrorMsg = s"Invalid results, results must be five characters and only contain (${validChars.mkString(", ")})"

  def apply(reader: LineReader, writer: LineWriter, validator: Validator): List[BlockColor] = {
    writer(PromptMsg)
    val input = reader().toUpperCase
    if (input.isEmpty) Nil
    else if (validator(input)) ConvertInputToColors(input, Nil)
    else {
      writer(ErrorMsg)
      apply(reader, writer, validator)
    }
  }
}
