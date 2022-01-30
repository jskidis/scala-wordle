package com.skidis.wordle

import BlockColor.{Black, BlockColor, Green, Yellow}

object ConvertInputToColors {
  def apply(input: String, colorPattern: List[BlockColor] = Nil): List[BlockColor] = {
    if (input.isEmpty) colorPattern
    else {
      val value = input.head.toUpper match {
        case `greenChar` => Green
        case `yellowChar` => Yellow
        case _ => Black
      }
      apply(input.tail, colorPattern :+ value)
    }
  }
}
