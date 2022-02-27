package com.skidis.wordle
package wordle

import input.BasicLenAndCharValidator

trait WordleGuessValidator extends BasicLenAndCharValidator with GuessValidator
  with WordleHintProps with WordleGuessProps {
  override def invalidCharMsg(validChars: Set[Char]) = s"Input may only contain letters"

  override def validateGuess(input: String): Option[String] = {
    validateGuess(input, guessWordLength, validGuessChars, validHintChars)
  }
}

object WordleGuessValidator extends WordleGuessValidator