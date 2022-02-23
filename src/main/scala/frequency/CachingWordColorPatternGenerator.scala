package com.skidis.wordle
package frequency

import scala.collection.mutable

trait CachingWordColorPatternGenerator extends WordColorPatternGenerator {
  val patternCache: mutable.Map[(XordleWord, XordleWord), ColorPattern] =
    collection.mutable.Map[(XordleWord, XordleWord), ColorPattern]()

  override def generateWordColorPattern(answer: XordleWord, word: XordleWord): ColorPattern = {
    patternCache.getOrElseUpdate((answer, word), super.generateWordColorPattern(answer, word))
  }
}
