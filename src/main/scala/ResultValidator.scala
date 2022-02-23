package com.skidis.wordle

trait ResultValidator {
  def resultLength: Int
  def validationErrorMsg: String

  def validateResult(input: String): Boolean = {
    if (input.length != 5) false
    else input.filter{ch => validBlockChars.contains(ch.toUpper)} == input
  }
}
