package com.skidis.wordle

package object wordle {
  case class SimpleWordleWord(phrase: String) extends XordlePhrase {
    override def compare(that: XordlePhrase): Int = that match {
      case w2: SimpleWordleWord => -phrase.compareTo(w2.phrase)
    }
  }

  case class WordleWordFrequencies(phrase: String, frequency: Double) extends XordlePhrase {
    override def compare(that: XordlePhrase): Int = that match {
      case w2: WordleWordFrequencies => frequency.compareTo(w2.frequency)
    }
  }

  object WordleInPosHint extends InPosHint {
    override def inputChar: Char = 'G'
    override def colorBlock: String = "\uD83D\uDFE9"
  }

  object WordleInWordHint extends InWordHint {
    override def inputChar: Char = 'Y'
    override def colorBlock: String = "\uD83D\uDFE8"
  }

  object WordleMissHint extends MissHint {
    override def inputChar: Char = 'B'
    override def colorBlock: String = "\u2B1C"
  }

  trait WordleHintProps extends HintProps {
    override def inPosHint: InPosHint = WordleInPosHint
    override def inWordHint: InWordHint = WordleInWordHint
    override def missHint: MissHint = WordleMissHint
  }
  object WordleHintProps extends WordleHintProps

  trait WordleGuessProps extends GuessProps {
    override def guessWordLength: Int = 5
    override def validGuessChars: Set[Char] = (('a' to 'z') ++ ('A' to 'Z')).toSet
    override def invalidGuessCharError: String = "Input may only contain letters"
  }
}
