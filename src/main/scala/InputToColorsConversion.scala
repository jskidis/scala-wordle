package com.skidis.wordle

import BlockColor.{Black, BlockColor, Green, Yellow}

import scala.annotation.tailrec

object InputToColorsConversion {
  @tailrec
  def convert(input: String, colorPattern: List[BlockColor] = Nil): List[BlockColor] = {
    if (input.isEmpty) colorPattern
    else {
      val value = input.head.toUpper match {
        case `greenChar` => Green
        case `yellowChar` => Yellow
        case _ => Black
      }
      convert(input.tail, colorPattern :+ value)
    }
  }
}
