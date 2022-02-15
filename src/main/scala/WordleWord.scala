package com.skidis.wordle

trait WordleWord extends Ordered[WordleWord] {
  def wordString(): String
}

case class SimpleWordleWord(word: String) extends WordleWord {
  override def wordString(): String = word
  override def compare(that: WordleWord): Int = that match {
    case w2: SimpleWordleWord => -word.compareTo(w2.wordString())
  }
}

case class WordleWordFrequencies(word: String, frequency: Double) extends WordleWord {
  override def wordString(): String = word
  override def compare(that: WordleWord): Int = that match {
    case w2: WordleWordFrequencies => frequency.compareTo(w2.frequency)
  }
}
