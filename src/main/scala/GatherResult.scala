package com.skidis.worlde

object GatherResult extends ResultChars {
  type LineReader = () => String
  type LineWriter = String => Unit
  type Validator = String => Boolean

  val PromptMsg = "Enter Results: "
  val ErrorMsg = s"Invalid results, results must be five characters and only contain (${validChars.mkString(", ")})"

  def apply(reader: LineReader, writer: LineWriter, validator: Validator): Option[String] = {
    writer(PromptMsg)
    val result = reader()
    if (result.isEmpty) None
    else if (validator(result)) Option(result)
    else {
      writer(ErrorMsg)
      apply(reader, writer, validator)
    }
  }
}
