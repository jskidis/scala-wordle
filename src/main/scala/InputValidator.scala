package com.skidis.wordle

object InputValidator {
  def apply(input: String): Boolean = {
    if (input.length != 5) false
    else input.filter{ch => validChars.contains(ch)} == input
  }
}
