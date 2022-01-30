package com.skidis.wordle

import BlockColors.BlockColors

object GatherResult {
  type LineReader = () => String
  type LineWriter = String => Unit
  type Validator = String => Boolean

  val PromptMsg = "Enter Results: "
  val ErrorMsg = s"Invalid results, results must be five characters and only contain (${validChars.mkString(", ")})"

  def apply(reader: LineReader, writer: LineWriter, validator: Validator): List[BlockColors] = {
    writer(PromptMsg)
    val result = reader()
    if (result.isEmpty) Nil
    else if (validator(result)) ConvertInputToColors(result.toLowerCase, Nil)
    else {
      writer(ErrorMsg)
      apply(reader, writer, validator)
    }
  }
}
