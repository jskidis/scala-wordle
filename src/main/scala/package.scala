package com.skidis

import com.skidis.wordle.BlockColor.BlockColor

package object wordle {
  type ColorPatternGenerator = String => List[BlockColor]
  type LineReader = () => String
  type LineWriter = String => Unit
  type Validator = String => Boolean

  val (greenChar, yellowChar, blackChar) = ('G', 'Y', 'B')
  val validChars = List(greenChar, yellowChar, blackChar)

  object BlockColor extends Enumeration {
    type BlockColor = String
    val Green = "\uD83D\uDFE9"
    val Yellow = "\uD83D\uDFE8"
    val Black = "\u2B1C"
  }

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

  case class WordClusterCount(word: WordleWord, clusterCount: Int)
}
