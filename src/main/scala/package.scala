package com.skidis

package object wordle {
  val (greenChar, yellowChar, blackChar) = ('g', 'y', 'b')

  val validChars = List(
    greenChar, yellowChar, blackChar,
    greenChar.toUpper, yellowChar.toUpper, blackChar.toUpper
  )

  object BlockColor extends Enumeration {
    type BlockColor = Value
    val Green, Yellow, Black = Value
  }

  case class MatchingDetail(positions: List[Option[Char]], otherLetters: Set[Char])
}
