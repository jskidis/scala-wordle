package com.skidis.wordle
package waffle

trait WaffleWriter extends Writer {
  def writeWordGrid(wordGrid: WaffleWordGrid): Unit = {
    writeLine(wordGrid(0).phrase)
    writeLine(s"${wordGrid(3).phrase(1)} ${wordGrid(4).phrase(1)} ${wordGrid(5).phrase(1)}")
    writeLine(wordGrid(1).phrase)
    writeLine(s"${wordGrid(3).phrase(3)} ${wordGrid(4).phrase(3)} ${wordGrid(5).phrase(3)}")
    writeLine(wordGrid(2).phrase)
    writeLine("")
  }

  def writeHintGrid(hintGrid: Seq[WordHints]): Unit = {
    writeLine(hintGrid(0).map(_.colorBlock).mkString)
    writeLine(s"${hintGrid(3)(1).colorBlock}⬛${hintGrid(4)(1).colorBlock}⬛${hintGrid(5)(1).colorBlock}")
    writeLine(hintGrid(1).map(_.colorBlock).mkString)
    writeLine(s"${hintGrid(3)(3).colorBlock}⬛${hintGrid(4)(3).colorBlock}⬛${hintGrid(5)(3).colorBlock}")
    writeLine(hintGrid(2).map(_.colorBlock).mkString)
    writeLine("")
  }
}
