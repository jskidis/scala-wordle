package com.skidis.wordle

import scala.io.BufferedSource

trait WordReader {
  def readWords(source: BufferedSource): Set[SimpleWordleWord] = {
    val lines = source.getLines.toSet
    lines.map(l => SimpleWordleWord(l.toUpperCase))
  }

  // Whole bunch of error checking I could be doing, but frankly the thrown exceptions are probably good enough for this use case
  def readWordFrequencies(source: BufferedSource): Set[WordleWordFrequencies] = {
    val lines = source.getLines.toSet
    lines.map { l =>
      val s = l.split(",")
      WordleWordFrequencies(s.head.trim.toUpperCase, s(1).trim.toDouble)
    }
  }
}

object WordReader extends WordReader
