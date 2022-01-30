package com.skidis

package object wordle {
  val (greenChar, yellowChar, blackChar) = ('G', 'Y', 'B')

  val validChars = List(
    greenChar, yellowChar, blackChar
  )

  object BlockColor extends Enumeration {
    type BlockColor = Value
    val Green, Yellow, Black = Value
  }

  case class MatchingDetail(positions: List[Option[Char]], otherLetters: Set[Char])
}
