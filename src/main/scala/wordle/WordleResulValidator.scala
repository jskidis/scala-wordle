package com.skidis.wordle
package wordle

import input.BasicLenAndCharValidator

trait WordleResulValidator extends BasicLenAndCharValidator with ResultValidator {
  override def validateResult(input: String): Option[String] = {
    validateInput(input, inputLength, validResultChars)
  }
}
object WordleResulValidator extends WordleResulValidator
