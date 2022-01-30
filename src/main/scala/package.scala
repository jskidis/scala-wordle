package com.skidis

package object wordle {

  val greenChar: Char = 'g'
  val yellowChar: Char = 'y'
  val blackChar: Char = 'b'

  val validChars = List(
    greenChar, yellowChar, blackChar,
    greenChar.toUpper, yellowChar.toUpper, blackChar.toUpper
  )

  object BlockColors extends Enumeration {
    type BlockColors = Value
    val Green, Yellow, Black = Value
  }

  case class LetterPattern(matchedPositions: List[Option[Char]], matchingLetters: List[Char])

}
