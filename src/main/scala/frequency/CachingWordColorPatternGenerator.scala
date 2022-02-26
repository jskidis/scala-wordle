package com.skidis.wordle
package frequency

import scala.collection.mutable

trait CachingWordColorPatternGenerator extends WordColorPatternGenerator {
  val patternCache: mutable.Map[(XordlePhrase, XordlePhrase), WordHints] =
    collection.mutable.Map[(XordlePhrase, XordlePhrase), WordHints]()

  override def generateWordColorPattern(answer: XordlePhrase, word: XordlePhrase, hintProps: HintProps): WordHints = {
    patternCache.getOrElseUpdate((answer, word), super.generateWordColorPattern(answer, word, hintProps))
  }
}
