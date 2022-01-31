package com.skidis.wordle

import BlockColor.{Black, BlockColor, Green, Yellow}

object WordColorsFromAnswer {
  // Proactively not doing case insensitive here for performance reasons, may revisit
  def apply(answer: String, word: String): List[BlockColor] = {
    word.zipWithIndex.map {
      case (letter, index) if letter == answer(index) => Green
      case (letter, _) if answer.contains(letter) => Yellow
      case _ => Black
    }.toList

  }
}
