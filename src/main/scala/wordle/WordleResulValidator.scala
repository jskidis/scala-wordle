package com.skidis.wordle
package wordle

import input.BasicLenAndCharValidator

trait WordleResulValidator extends ResultValidator with BasicLenAndCharValidator
  with WordleGuessProps with WordleHintProps {

  override def validateResult(input: String): Option[String] = {
    validateInput(input, guessWordLength, validHintChars)
  }
}
object WordleResulValidator extends WordleResulValidator
