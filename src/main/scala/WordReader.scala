package com.skidis.wordle

import scala.io.BufferedSource

object WordReader {
  def read(source: BufferedSource): Set[String] = {
    val words = source.getLines.toSet
    words.map(_.toUpperCase)
  }
}
