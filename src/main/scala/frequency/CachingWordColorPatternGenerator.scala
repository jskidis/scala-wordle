package com.skidis.wordle
package frequency

import scala.collection.mutable

trait CachingWordColorPatternGenerator extends WordColorPatternGenerator {
  val patternCache: mutable.Map[(XordlePhrase, XordlePhrase), ColorPattern] =
    collection.mutable.Map[(XordlePhrase, XordlePhrase), ColorPattern]()

  override def generateWordColorPattern(answer: XordlePhrase, word: XordlePhrase): ColorPattern = {
    patternCache.getOrElseUpdate((answer, word), super.generateWordColorPattern(answer, word))
  }
}
