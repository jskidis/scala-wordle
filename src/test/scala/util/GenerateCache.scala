package com.skidis.wordle
package util

import wordle.WordleHintProps

import java.io.{BufferedWriter, File, FileWriter}
import scala.io.Source

object GenerateCache extends App with WordReader with WordHintsGenerator with WordleHintProps {
  val wordSet = readWords(Source.fromResource("word-frequency-filtered.txt")).
    toSeq.map(_.text).sorted

  val file = new File("src/test/resources/pattern-cache-brine.txt")
  val writer = new BufferedWriter(new FileWriter(file))

  //  wordSet.foreach { answer =>
  wordSet.filter(_ == "BRINE").foreach { answer =>
    wordSet.map { word =>
      (word, generateWordHints(answer, word).map(_.inputChar).mkString(""))
    }.groupBy {
      case (_, wh) => wh
    }.foreach {
      case (hints, seq: Seq[(String, String)]) =>
        writer.write(s"$answer=$hints:${seq.map(_._1).mkString(",")}")
        writer.newLine()
        writer.flush()
    }
  }
}
