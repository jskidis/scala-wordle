package com.skidis.wordle
package input

import scala.annotation.tailrec

trait ResultInput extends LineReader with Writer with ResultValidator with InputToHintConversion {
  def resultPrompt: String = "Enter Results: "

  @tailrec
  final def generatePattern(): WordHints = {
    writeString(resultPrompt)
    val input = readLine().toUpperCase

    if (input.isEmpty) Nil
    else validateResult(input) match {
      case None => convertInputToHints(input)
      case Some(error) =>
        writeLine(error)
        generatePattern()
    }
  }
}
