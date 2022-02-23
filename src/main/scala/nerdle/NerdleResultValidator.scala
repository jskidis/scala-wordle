package com.skidis.wordle
package nerdle

import input.BasicLenAndCharValidator

trait NerdleResultValidator extends BasicLenAndCharValidator with ResultValidator {
  override def validateResult(input: String): Option[String] = {
    validateInput(input, inputLength, validResultChars)
  }
}

object NerdleResultValidator extends NerdleResultValidator
