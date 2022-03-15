package com.skidis.wordle
package waffle

trait CharFreqMapGenerator {
  def generateCharFreqMap(str: Seq[Char]): Map[Char, Int] = {
    val grouped = str.groupBy{ ch: Char => ch.toUpper }
    grouped.map { case (ch: Char, seq: Seq[Char]) => (ch, seq.size) }
  }
}

object CharFreqMapGenerator extends CharFreqMapGenerator
