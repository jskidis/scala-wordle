package com.skidis

import com.skidis.wordle.BlockColor.{BlockColor, Green}

package object wordle {
  type ColorPatternGenerator = String => List[BlockColor]

  val (greenChar, yellowChar, blackChar) = ('G', 'Y', 'B')
  val validChars = List(greenChar, yellowChar, blackChar)

  object BlockColor extends Enumeration {
    type BlockColor = String
    val Green = "\uD83D\uDFE9"
    val Yellow = "\uD83D\uDFE8"
    val Black = "\u2B1C"
  }

  val winningColorPattern = List(Green, Green, Green, Green, Green)

  case class WordClusterCount(word: String, clusterCount: Int)
}
