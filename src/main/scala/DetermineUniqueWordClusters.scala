package com.skidis.wordle

object DetermineUniqueWordClusters {
  def apply(word: String, wordSet: Set[String]): Int = {
    wordSet.map(w => WordColorsFromAnswer(word, w)).size
  }
}
