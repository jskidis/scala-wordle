package com.skidis.wordle
package frequency

import scala.io.Source

object OutputQueue extends App with WordReader {
  val cachedGenerator = new CachingWordColorPatternGenerator {}
  val wordSet = readWords(Source.fromResource("answers.txt")).take(10)

  val wordClusters = wordSet.toList.map { potentialAnswer: XordlePhrase =>
    wordSet.map { word: XordlePhrase => cachedGenerator.generateWordColorPattern(potentialAnswer, word) }.size
  }

  cachedGenerator.patternCache.foreach {
    case ((answer, word), pattern) => println(s"${answer.phrase},${word.phrase}=>${pattern.mkString}")
  }
}
