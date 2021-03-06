package com.skidis.wordle
package strategy

import scala.util.Random

trait RandomGuessStrategy extends HardModeWordElimStrategy {
  override def generateNextGuesses(remainingWords: WordSet, previousGuesses: Seq[(String, WordHints)], number: Int)
  : Seq[String] = {
    Random.shuffle(remainingWords.map{w: XrdleWord => w.text}.toVector).take(number)
  }
}
