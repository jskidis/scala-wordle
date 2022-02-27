package com.skidis.wordle
package input

trait BasicLenAndCharValidator  {
  def lengthMsg(len: Int): String = s"Input must be $len characters"
  def invalidCharMsg(validChars: Set[Char]): String = s"Input may only contain ${validChars.mkString(", ")}"
  def wasResultMsg: String = "That was a word, not a result"

  def validateInput(input: String, len: Int, validChars: Set[Char]): Option[String] = {
    if (input.length != len) Option(lengthMsg(len))
    else if (input.filter { ch => validChars.contains(ch) } != input) Option(invalidCharMsg(validChars))
    else None
  }

  def validateGuess(input: String, len: Int, validChars: Set[Char], resultChars: Set[Char]): Option[String] = {
    validateInput(input, len, validChars) match {
      case Some(errorMsg) => Some(errorMsg)
      case None => if (input.forall(ch => resultChars.contains(ch))) Some(wasResultMsg) else None
    }
  }
}

object BasicLenAndCharValidator extends BasicLenAndCharValidator
