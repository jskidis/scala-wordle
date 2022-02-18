package com.skidis.wordle

object StandardResultValidator {
  def validate(input: String): Boolean = {
    if (input.length != 5) false
    else input.filter{ch => validBlockChars.contains(ch.toUpper)} == input
  }
}

object StandardGuessValidator {
  def validate(input: String): Boolean = {
    if (input.length != 5) false
    else if (!input.forall(_.isLetter)) false
    // make sure word isn't just all color characters, i.e. entering result instead of guess
    else input.exists(ch => !validBlockChars.contains(ch))
  }
}
