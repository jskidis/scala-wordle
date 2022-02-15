package com.skidis.wordle

import BlockColor.{Black, Green, Yellow}

object InputToColorsConversion {
  def convert(input: String): ColorPattern = {
    input.map { ch =>
      ch.toUpper match {
        case `greenChar` => Green
        case `yellowChar` => Yellow
        case _ => Black
      }
    }.toList
  }
}
