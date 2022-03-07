package com.skidis.wordle
package strategy

import scala.util.Random

trait RandomGuessStrategy extends StandardWordElimStrategy {
  override def generateNextGuesses(remainingWords: WordSet, number: Int): Vector[XordlePhrase] = {
    Random.shuffle(remainingWords.toVector).take(number)
  }
}
