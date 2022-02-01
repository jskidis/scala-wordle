package com.skidis

import com.skidis.wordle.BlockColor.{BlockColor, Green}

package object wordle {
  type ColorPatternGenerator = String => List[BlockColor]

  val (greenChar, yellowChar, blackChar) = ('G', 'Y', 'B')
  val validChars = List(greenChar, yellowChar, blackChar)

  object BlockColor extends Enumeration {
    type BlockColor = Value
    val Green, Yellow, Black = Value
  }

  val winningColorPattern = List(Green, Green, Green, Green, Green)
}
