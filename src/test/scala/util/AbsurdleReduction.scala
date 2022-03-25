package com.skidis.wordle
package util

import hintgen.WordHintsGenerator
import wordle.{WordReader, WordleHintProps}

import scala.annotation.tailrec
import scala.io.Source

object AbsurdleReduction extends App with WordReader with WordHintsGenerator with WordleHintProps {
  type WordSeq = Seq[String]
  case class WordToWordSet(wordChain: Seq[(String, Seq[WordHints])], wordSet: WordSeq)

  var howManyEachRound = 25
  var answers = readWords(Source.fromResource("answers.txt")).toSeq.map(_.text)

  val results = generateRounds(Seq(WordToWordSet(Nil, answers)))

  results.foreach{ w2wSet: WordToWordSet =>
    val answers = w2wSet.wordChain.map { case (w: String, h: Seq[WordHints]) =>
      s"$w ${h.map(_.map(_.colorBlock).mkString("")).mkString("||")}"
    }.mkString(", ")
    println(s"$answers - ${w2wSet.wordSet.size} remaining choices")
  }

  @tailrec
  private def generateRounds(topW2WSets: Seq[WordToWordSet], round: Int = 0): Seq[WordToWordSet] = {
    if (round == 3) topW2WSets
    else {
      val nextTopSets = topW2WSets.flatMap { w2wSet: WordToWordSet =>
        determineNextWordSets(w2wSet)
      }.sortBy { w2wSet: WordToWordSet =>
        w2wSet.wordSet.size
      }.take(howManyEachRound)
      generateRounds(nextTopSets, round + 1)
    }
  }

  def determineNextWordSets(w2wSet: WordToWordSet): Seq[WordToWordSet] = {
    w2wSet.wordSet.map{ word =>
      (word, determineMaxOptionsForWord(word, w2wSet.wordSet))
    }.sortBy {
      case (_, (_, seq: Seq[_])) => seq.size
    }.take(howManyEachRound).map{
      case (word: String, (hintSeq: Seq[_], wordSeq: Seq[_])) =>
        WordToWordSet(w2wSet.wordChain :+ (word, hintSeq), wordSeq)
    }
  }

  def determineMaxOptionsForWord(guess: String, words: WordSeq): (Seq[WordHints], WordSeq) = {
    val clusters: Seq[(WordHints, WordSeq)] = generateClusters(guess, words)

    val maxSize = clusters.maxBy {
      case (_, seq: WordSeq) => seq.size
    }._2.size

    val (wordHintsSet, wordSets) = clusters.filter {
      case (_, seq: WordSeq) => seq.size == maxSize
    }.unzip

    (wordHintsSet, wordSets.flatten)
  }

  def generateClusters(guess: String, words: WordSeq): Seq[(WordHints, WordSeq)] = {
    words.filter { w => w != guess
    }.map{ candidate => (generateWordHints(candidate, guess), candidate)
    }.groupBy {
      case (h: WordHints, _) => h
    }.map {
      case (h: WordHints, seq: Seq[_]) => (h, seq.map { case (_, w: String) => w })
    }.toSeq
  }
}
