package com.skidis.wordle

trait WordleWord extends Ordered[WordleWord] {
  def string: String
}

case class SimpleWordleWord(string: String) extends WordleWord {
  override def compare(that: WordleWord): Int = that match {
    case w2: SimpleWordleWord => -string.compareTo(w2.string)
  }
}

case class WordleWordFrequencies(string: String, frequency: Double) extends WordleWord {
  override def compare(that: WordleWord): Int = that match {
    case w2: WordleWordFrequencies => frequency.compareTo(w2.frequency)
  }
}
