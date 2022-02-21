package com.skidis.wordle

import BlockColor.{Blank, Green, Yellow}

trait InputToColorsConversion {
  def convertInputToColors(input: String): ColorPattern = {
    input.map { ch =>
      ch.toUpper match {
        case `greenChar` => Green
        case `yellowChar` => Yellow
        case _ => Blank
      }
    }.toList
  }
}

object InputToColorsConversion extends InputToColorsConversion