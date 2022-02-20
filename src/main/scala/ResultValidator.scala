package com.skidis.wordle

trait ResultValidator {
  def validateResult(input: String): Boolean = {
    if (input.length != 5) false
    else input.filter{ch => validBlockChars.contains(ch.toUpper)} == input
  }
}

object ResultValidator extends ResultValidator




