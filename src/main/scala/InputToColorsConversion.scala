package com.skidis.wordle

import BlockColor.{Black, BlockColor, Green, Yellow}

object InputToColorsConversion {
  def convert(input: String): List[BlockColor] = {
    input.map { ch =>
      ch.toUpper match {
        case `greenChar` => Green
        case `yellowChar` => Yellow
        case _ => Black
      }
    }.toList
  }
}
