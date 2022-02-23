package com.skidis.wordle

import input.BasicResultValidator

package object wordle {
  trait WordleResulValidator extends BasicResultValidator {
    override def resultLength: Int = 5
    override def validChars: List[Char] = validBlockChars
  }
  object WordleResulValidator extends WordleResulValidator
}
