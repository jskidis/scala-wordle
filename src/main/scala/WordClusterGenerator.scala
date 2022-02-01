package com.skidis.wordle

object WordClusterGenerator {
  def generateUnique(word: String, wordSet: Set[String]): Int = {
    wordSet.map(w => WordColorPatternGenerator.generate(word, w)).size
  }
}
