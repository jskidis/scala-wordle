package com.skidis.wordle

import scala.io.BufferedSource

object WordReader {
  def readWords(source: BufferedSource): Set[SimpleWordleWord] = {
    val words = source.getLines.toSet
    words.map(w => SimpleWordleWord(w.toUpperCase))
  }
}
