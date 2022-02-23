package com.skidis.wordle

package object wordle {
  trait WordleResulValidator extends ResultValidator {
    def resultLength: Int = 5
    def validationErrorMsg: String =
      s"Invalid results, results must be 5 characters and only contain (${validBlockChars.mkString(", ")})"
  }
  object WordleResulValidator extends WordleResulValidator
}
