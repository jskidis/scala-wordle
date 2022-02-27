package com.skidis.wordle
package nerdle

import input.BasicLenAndCharValidator

trait NerdleResultValidator extends ResultValidator with BasicLenAndCharValidator
  with HintProps with GuessProps {

  override def validateResult(input: String): Option[String] = {
    validateInput(input, guessWordLength, validHintChars)
  }
}
