package com.skidis.wordle
package frequency

import scala.collection.mutable

trait CachingWordColorPatternGenerator extends WordColorPatternGenerator {
  val patternCache: mutable.Map[(WordleWord, WordleWord), ColorPattern] =
    collection.mutable.Map[(WordleWord, WordleWord), ColorPattern]()

  override def generateWordColorPattern(answer: WordleWord, word: WordleWord): ColorPattern = {
    patternCache.getOrElseUpdate((answer, word), super.generateWordColorPattern(answer, word))
  }
}
