package com.skidis.wordle
package wordle

case class SimpleWordleWord(phrase: String) extends XordlePhrase {
  override def compare(that: XordlePhrase): Int = that match {
    case w2: SimpleWordleWord => -phrase.compareTo(w2.phrase)
  }
}

case class WordleWordFrequencies(phrase: String, frequency: Double) extends XordlePhrase {
  override def compare(that: XordlePhrase): Int = that match {
    case w2: WordleWordFrequencies => frequency.compareTo(w2.frequency)
  }
}

