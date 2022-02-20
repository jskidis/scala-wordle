package com.skidis.wordle

trait ResultValidator {
  def validateResult(input: String): Boolean = {
    if (input.length != 5) false
    else input.filter{ch => validBlockChars.contains(ch.toUpper)} == input
  }
}

object ResultValidator extends ResultValidator


trait GuessValidator {
  def validateGuess(input: String): Boolean = {
    if (input.length != 5) false
    else if (!input.forall(_.isLetter)) false
    // make sure word isn't just all color characters, i.e. entering result instead of guess
    else input.exists(ch => !validBlockChars.contains(ch))
  }
}

object GuessValidator extends GuessValidator