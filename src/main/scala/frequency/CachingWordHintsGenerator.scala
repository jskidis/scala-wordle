package com.skidis.wordle
package frequency

import scala.collection.mutable

trait CachingWordHintsGenerator extends WordHintsGenerator {
  val patternCache: mutable.Map[(XordlePhrase, XordlePhrase), WordHints] =
    collection.mutable.Map[(XordlePhrase, XordlePhrase), WordHints]()

  override def generateWordHints(answer: XordlePhrase, word: XordlePhrase, hintProps: HintProps): WordHints = {
    patternCache.getOrElseUpdate((answer, word), super.generateWordHints(answer, word, hintProps))
  }
}
