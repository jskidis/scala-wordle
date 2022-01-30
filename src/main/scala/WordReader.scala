package com.skidis.worlde

import scala.io.BufferedSource

object WordReader {
  def apply(source: BufferedSource): Set[String] = {
    val words = source.getLines.toSet
    words.map(_.toUpperCase)
  }
}
