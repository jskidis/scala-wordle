package com.skidis.wordle

import BlockColor.{Black, Green, Yellow}

trait InputToColorsConversion {
  def convertInputToColors(input: String): ColorPattern = {
    input.map { ch =>
      ch.toUpper match {
        case `greenChar` => Green
        case `yellowChar` => Yellow
        case _ => Black
      }
    }.toList
  }
}

object InputToColorsConversion extends InputToColorsConversion