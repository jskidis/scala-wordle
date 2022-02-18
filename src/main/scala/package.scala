package com.skidis

import com.skidis.wordle.BlockColor.BlockColor

package object wordle {
  type ColorPattern = List[BlockColor]
  type WordSet = Set[_ <: WordleWord]

  type ColorPatternGenerator = String => ColorPattern
  type GuessGatherer = String => String
  type LineReader = () => String
  type LineWriter = String => Unit
  type Validator = String => Boolean

  object BlockColor extends Enumeration {
    type BlockColor = String
    val Green = "\uD83D\uDFE9"
    val Yellow = "\uD83D\uDFE8"
    val Black = "\u2B1C"
  }

  val (greenChar, yellowChar, blackChar) = ('G', 'Y', 'B')
  val validBlockChars = List(greenChar, yellowChar, blackChar)
}
