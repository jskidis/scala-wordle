package com.skidis.wordle

import BlockColor.BlockColor

object GenerateWordClusters {
  def apply(word: String, wordSet: Set[String]): Set[List[BlockColor]] = {
    wordSet.map(w => WordColorsFromAnswer(word, w))
  }
}
