package com.skidis.wordle
package strategy

import scala.util.Random

trait RandomGuessStrategy extends StandardWordElimStrategy {
  override def generateNextGuesses(remainingWords: WordSet, previousGuesses: Seq[(String, WordHints)], number: Int)
  : Seq[String] = {
    Random.shuffle(remainingWords.map{w: XordlePhrase => w.phrase}.toVector).take(number)
  }
}
