package com.skidis.wordle
package wordle

import input.BasicLenAndCharValidator

trait WordleGuessValidator extends BasicLenAndCharValidator with GuessValidator {
  override def invalidCharMsg(validChars: Seq[Char]) = s"Input may only contain letters"

  override def validateGuess(input: String): Option[String] = {
    validateGuess(input, inputLength, valueGuessChars, validResultChars)
  }
}

object WordleGuessValidator extends WordleGuessValidator