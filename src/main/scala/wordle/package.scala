package com.skidis.wordle

package object wordle {
  val inputLength: Int = 5
  val validResultChars: Seq[Char] = validBlockChars
  val valueGuessChars: Seq[Char] = ('a' to 'z') ++ ('A' to 'Z')
}
