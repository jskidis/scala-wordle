package com.skidis.wordle
package wordle.runner

import runners.XrdleInteractiveRunner

trait WordleInteractiveStandardRunner extends XrdleInteractiveRunner with WordleStandardRunner
object WordleInteractiveStandardRunner extends App with WordleInteractiveStandardRunner {
  printWordleBlock(runInteractive(), maxGuesses)
}

trait WordleInteractiveAnswerOnlyRunner extends XrdleInteractiveRunner with WordleAnswerOnlyRunner
  object WordleInteractiveAnswerOnlyRunner extends App with WordleInteractiveAnswerOnlyRunner {
    printWordleBlock(runInteractive(), maxGuesses)
}

trait WordleInteractiveCharFreqRunner extends XrdleInteractiveRunner with WordleCharFreqRunner
object WordleInteractiveCharFreqRunner extends App with WordleInteractiveCharFreqRunner {
  printWordleBlock(runInteractive(), maxGuesses)
}

trait WordleInteractiveWordFreqRunner extends XrdleInteractiveRunner with WordleWordFreqRunner
object WordleInteractiveWordFreqRunner extends App with WordleInteractiveWordFreqRunner {
  printWordleBlock(runInteractive(), maxGuesses)
}

trait WordleInteractiveReverseRunner extends XrdleInteractiveRunner with WordleReverseRunner
object WordleInteractiveReverseRunner extends App with WordleInteractiveReverseRunner {
  printWordleBlock(runInteractive(), maxGuesses)
}

trait WordleInteractiveRandomRunner extends XrdleInteractiveRunner with WordleRandomRunner
object WordleInteractiveRandomRunner extends App with WordleInteractiveRandomRunner {
  printWordleBlock(runInteractive(), maxGuesses)
}
