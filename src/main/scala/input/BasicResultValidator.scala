package com.skidis.wordle
package input

trait BasicResultValidator extends ResultValidator {
  def resultLength: Int

  def validChars: List[Char]

  val invalidLengthMsg: String = s"Result must be $resultLength characters"
  val invalidCharMsg: String = s"Result may only contain ${validChars.mkString(", ")}"

  def validateResult(input: String): Option[String] = {
    if (input.length != 5) Option(invalidLengthMsg)
    else if (input.filter { ch => validChars.contains(ch.toUpper) } != input) Option(invalidCharMsg)
    else None
  }
}
