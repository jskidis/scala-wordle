package com.skidis.wordle

import BlockColors.{Black, BlockColors, Green, Yellow}

object ConvertInputToColors {
  def apply(input: String, results: List[BlockColors] = Nil): List[BlockColors] = {
    if (input.isEmpty) results
    else {
      val value = input.head match {
        case `greenChar` => Green
        case `yellowChar` => Yellow
        case `blackChar` => Black
      }
      apply(input.tail, results :+ value)
    }
  }
}
