package com.skidis.wordle

object TestFixtures {
  val inPosChar: Char = TInPosHint.inputChar
  val inWordChar: Char = TInWordHint.inputChar
  val missChar: Char = TMissHint.inputChar

  object TInPosHint extends InPosHint {
    override def inputChar: Char = 'G'
    override def colorBlock: String = "\uD83D\uDFE9"
  }

  object TInWordHint extends InWordHint {
    override def inputChar: Char = 'Y'
    override def colorBlock: String = "\uD83D\uDFE8"
  }

  object TMissHint extends MissHint {
    override def inputChar: Char = 'B'
    override def colorBlock: String = "\u2B1C"
  }

  trait TestHintProps extends HintProps {
    override def inPosHint: InPosHint = TInPosHint
    override def inWordHint: InWordHint = TInWordHint
    override def missHint: MissHint = TMissHint
  }
  object TestHintProps extends TestHintProps

  trait TestGuessProps extends GuessProps {
    override def guessWordLength: Int = 5
    override def maxGuesses: Int = 6
    override def validGuessChars: Set[Char] = (('a' to 'z') ++ ('A' to 'Z')).toSet
    override def invalidGuessCharError: String = "Invalid Characters"
  }
  object TestGuessProps extends TestGuessProps

  case class TWord(text: String) extends XrdleWord

  trait SolveStrategyWithReduceWordSetFixture extends SolveStrategy {
    override def reduceWordSet(wordSet: WordSet,
      currentGuess: String, wordHints: WordHints)
    : WordSet = wordSet
  }

  trait SolveStrategyWithNextGuessFixture {
    def generateNextGuesses(remainingWords: WordSet,
      previousGuesses: Seq[(String, WordHints)], numToReturn: Int)
    : Seq[String] = remainingWords.take(numToReturn).map(_.text).toSeq
  }
}
