package spaceX

import spaceX.blocks.Block

import scala.collection.mutable.ListBuffer

class SpaceStation(blocks: List[List[Block]]) {

  def build: String = {
    val width = stationWidth
    val station = new StringBuilder(stationHeight * width)
    for (line <- blocks) station ++= horizontalAlign(line, width)
    station.toString
  }

  def slowlyPrint(millis: Long): Unit = {
    for (symbol <- build) {
      if (symbol == ' ' || symbol == '\n') {
        print(symbol)
      } else {
        print('\u2588')
        Thread.sleep(millis)
        print("\b")
        print(symbol)
        Thread.sleep(millis)
      }
    }
  }

  def stationWidth: Int = blocks.map(elem => totalWidth(elem)).max

  def stationHeight: Int = blocks.map(elem => elem.map(elem => elem.height).max).sum

  private def maxHeight(blockLine: List[Block]): Int = blockLine.map(elem => elem.height).max

  private def totalWidth(blockLine: List[Block]): Int = blockLine.map(elem => elem.width).sum

  private def getLine(block: String, blockWidth: Int, lineNumber: Int): String = {
    val line = new StringBuilder(blockWidth)
    for (i <- 0 until blockWidth) line += block(i + lineNumber * blockWidth)
    line.toString
  }

  private def horizontalAlign(line: List[Block], newWidth: Int): String = {
    val oldWidth = totalWidth(line)
    val newHeight = maxHeight(line)
    val oldLine = verticalAlign(line, newHeight)
    val newLine = new StringBuilder(newHeight * newWidth)
    val left = Math.abs((newWidth - oldWidth) / 2)
    val right = Math.abs(newWidth - oldWidth - left)
    val newLeft = " " * left
    val newRight = " " * right
    for (i <- 0 until newHeight) newLine ++= newLeft + getLine(oldLine, oldWidth, i) + newRight + "\n"
    newLine.toString
  }

  private def verticalAlign(line: List[Block], newHeight: Int): String = {
    val lineBlocks = ListBuffer[String]()
    val lineBlock = new StringBuilder(newHeight * totalWidth(line))
    for (block <- line) {
      val top = Math.abs((newHeight - block.height) / 2)
      val bottom = Math.abs(newHeight - block.height - top)
      val newTop = " " * (top * block.width)
      val newBottom = " " * (bottom * block.width)
      lineBlocks.append(newTop + block.create + newBottom)
    }
    for (i <- 0 until newHeight)
      for (j <- lineBlocks.indices)
        lineBlock ++= getLine(lineBlocks(j), line(j).width, i)
    lineBlock.toString
  }

}

object SpaceStation {
  def apply(blocks: List[List[Block]]): SpaceStation = new SpaceStation(blocks)
}