package com.skidis.wordle

import BlockColor.{BlockColor, Green}

import scala.annotation.tailrec

object WordleProcessor {
  val winningColorPattern = List(Green, Green, Green, Green, Green)

  @tailrec
  def process(colorPatternGenerator: ColorPatternGenerator,
    lineWriter: LineWriter = Console.println)
  ( wordSet: Set[_ <: WordleWord],
    currentGuess: String = "SLATE",
    guesses: List[(String, List[BlockColor])] = Nil)
  : List[(String, List[BlockColor])] = {

    lineWriter(s"\n${List.fill(40)('*').mkString}")
    lineWriter(s"Current Guess: $currentGuess, Guess #:${guesses.size +1}")

    // Generate Color Pattern Based On Input or Answer or whatever function turns a guess into a color pattern
    val colorPattern = if(wordSet.size > 1) colorPatternGenerator(currentGuess) else winningColorPattern

    if (wordSet.size == 1) lineWriter("Only 1 choice left!!!")
    lineWriter(s"${colorPattern.mkString}")

    val updatedGuesses = guesses :+ (currentGuess, colorPattern)

    if (colorPattern.isEmpty) Nil // User entered an empty string, so abort without finishing
    else if (colorPattern == winningColorPattern) updatedGuesses // color pattern is all green, so word has been guessed
    else {
      // Create list of tuple with each letter of the current word and the color for that letter
      val wordPattern: List[(Char, BlockColor)] = currentGuess.toList zip colorPattern

      // Eliminate words from set that don't fit color pattern for current answer
      val remainingWords = wordSet.filter(w => WordPatternMatcher.doesWordMatch(
        w.wordString(), wordPattern) && w.wordString() != currentGuess)

      // Generate a set for each remaining word identifying the number unique clusters choosing that word would created
      val wordClusters: List[WordClusterCount] = remainingWords.toList.map { word =>
        WordClusterCount tupled (word, remainingWords.map{ w =>
          WordColorPatternGenerator.generate(word, w)
        }.size)
      }

      // Start over with next guess
      val nextGuess = determineNextGuess(wordClusters)
      lineWriter(s"Remaining Words: ${remainingWords.size}\nMost Unique Clusters: ${nextGuess.clusterCount}")
      process(colorPatternGenerator, lineWriter)(remainingWords, nextGuess.word.wordString(), updatedGuesses)
    }
  }

  private def determineNextGuess(wordClusters: List[WordClusterCount]): WordClusterCount = {

    def sortWordCluster(wc1: WordClusterCount, wc2: WordClusterCount): Boolean = {
      if(wc1.clusterCount != wc2.clusterCount) wc1.clusterCount > wc2.clusterCount
      else wc1.word > wc2.word
    }
    // Next Guess is based on word with most unique clusters, with ties resolved based on type
    val sortedClustersByWord = wordClusters.sortWith(sortWordCluster)
    sortedClustersByWord.head
  }
}
