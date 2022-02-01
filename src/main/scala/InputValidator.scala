package com.skidis.wordle

object InputValidator {
  def validate(input: String): Boolean = {
    if (input.length != 5) false
    else input.filter{ch => validChars.contains(ch.toUpper)} == input
  }
}
