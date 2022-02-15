package com.skidis.wordle

import scala.io.BufferedSource

object WordReader {
  def readWords(source: BufferedSource): Iterator[SimpleWordleWord] = {
    val lines = source.getLines
    lines.map(l => SimpleWordleWord(l.toUpperCase))
  }

  // Whole bunch of error checking I could be doing, but frankly the thrown exceptions are probably good enough for this use case
  def readWordFrequencies(source: BufferedSource): Iterator[WordleWordFrequencies] = {
    source.getLines.map  { l =>
      val s = l.split(",")
      WordleWordFrequencies(s.head.trim.toUpperCase, s(1).trim.toDouble)
    }
  }
}
