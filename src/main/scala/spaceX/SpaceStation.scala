package spaceX

import spaceX.blocks._

import scala.collection.mutable.ListBuffer

class SpaceStation(blocks: List[List[Block]]) {

  def build: String = {
    val width = stationWidth
    val height = stationHeight
    val station = new StringBuilder(height * width)
    for (line <- blocks) station ++= horizontalAlign(line, width)
    station.toString
  }

  def slowlyPrint(millis: Long): Unit = {
    def printLine(ch: Char, len: Int): Unit = {
      print("\u2588" * len)
      Thread.sleep(millis)
      print("\b" * len)
      print(ch.toString * len)
      Thread.sleep(millis)
    }

    val station = build
    for (i <- station.indices) {
      station(i) match {
        case symbol if Set(' ', '\n').contains(symbol) => print(symbol)
        case symbol if symbol == '\u2591' => if (station(i - 1) != '\u2591')
          printLine(symbol, station.substring(i).takeWhile(_ == '\u2591').length)
        case symbol if symbol == '-' => if (station(i - 1) != '-')
          printLine(symbol, station.substring(i).takeWhile(_ == '-').length)
        case symbol => printLine(symbol, 1)
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
    for (i <- 0 until newHeight) newLine ++= s"${" " * left}${getLine(oldLine, oldWidth, i)}${" " * right}\n"
    newLine.toString
  }

  private def verticalAlign(line: List[Block], newHeight: Int): String = {
    val lineBlock = new StringBuilder(newHeight * totalWidth(line))
    val lineBlocks = for (block <- line;
                          top = Math.abs((newHeight - block.height) / 2);
                          bottom = Math.abs(newHeight - block.height - top))
      yield s"${" " * (top * block.width)}${block.create}${" " * (bottom * block.width)}"
    for (i <- 0 until newHeight)
      for (j <- lineBlocks.indices)
        lineBlock ++= getLine(lineBlocks(j), line(j).width, i)
    lineBlock.toString
  }

}

object SpaceStation {
  def apply(blocks: List[List[Block]]): SpaceStation = new SpaceStation(blocks)

  def parseStation(blocks: Array[String]): List[List[Block]] = {
    val stationInfo = ListBuffer[List[Block]]()
    val lineInfo = ListBuffer[Block]()
    for (line <- blocks) {
      for (block <- line.split('|')) {
        val info = block.split(',')
        lineInfo.append(
          info.head match {
            case inf if inf == SP.toString => SolarPanel(info(1).toInt, info(2).toInt, info(3))
            case inf if inf == HM.toString => HumanModule(info(1).toInt, info(2).toInt, info(3))
            case inf if inf == TM.toString => TerminalModule(info(1).toInt, info(2).toInt, info(3))
            case _ => throw new Exception("WTF")
          }
        )
      }
      stationInfo.append(lineInfo.toList)
      lineInfo.clear()
    }
    stationInfo.toList
  }
}