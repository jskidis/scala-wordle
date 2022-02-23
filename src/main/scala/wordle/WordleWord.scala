package com.skidis.wordle
package wordle

case class SimpleWordleWord(string: String) extends XordleWord {
  override def compare(that: XordleWord): Int = that match {
    case w2: SimpleWordleWord => -string.compareTo(w2.string)
  }
}

case class WordleWordFrequencies(string: String, frequency: Double) extends XordleWord {
  override def compare(that: XordleWord): Int = that match {
    case w2: WordleWordFrequencies => frequency.compareTo(w2.frequency)
  }
}
