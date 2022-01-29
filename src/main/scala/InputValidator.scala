package com.skidis.worlde

object InputValidator extends ResultChars {
  def apply(input: String): Boolean = {
    if (input.length != 5) false
    else input.filter{ch => validChars.contains(ch)} == input
  }
}
