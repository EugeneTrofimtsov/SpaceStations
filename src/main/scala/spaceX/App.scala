package spaceX

import spaceX.blocks._

import scala.collection.mutable.ListBuffer

object App {

  def defaultStation(): Unit = {
    SpaceStation(List(
      List(TerminalModule(3, 6, "cap")),
      List(SolarPanel(2, 12, "panel"), HumanModule(6, 8, "room"), SolarPanel(2, 12, "panel")),
      List(HumanModule(4, 4, "gateway")),
      List(SolarPanel(2, 12, "panel"), HumanModule(8, 12, "room"), SolarPanel(2, 12, "panel"))
    )).slowlyPrint(250)
  }

  val defaultStationArr: Array[String] = Array(
    "TM,3,6,cap",
    "SP,2,12,panel|HM,6,8,room|SP,2,12,panel",
    "HM,4,4,gateway",
    "SP,2,12,panel|HM,8,12,room|SP,2,12,panel"
  )

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

  def main(args: Array[String]): Unit = {
    //defaultStation()
    println(SpaceStation(parseStation(defaultStationArr)).build)//.slowlyPrint(250)
  }

}
