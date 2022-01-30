package com.skidis.wordle

import BlockColors.{Black, BlockColor, Green, Yellow}

object ConvertInputToColors {
  def apply(input: String, results: List[BlockColor] = Nil): List[BlockColor] = {
    if (input.isEmpty) results
    else {
      val value = input.head match {
        case `greenChar` => Green
        case `yellowChar` => Yellow
        case _ => Black
      }
      apply(input.tail, results :+ value)
    }
  }
}
