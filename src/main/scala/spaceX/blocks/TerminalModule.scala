package spaceX.blocks

class TerminalModule(override val height: Int, override val width: Int, override val code: String) extends Block {
  require(width == 2 * height, "width must be greater than height in 2 times")

  val moduleType: ModuleType = TM

  private def createLine(LeftPos: Int, RightPos: Int): String = {
    val line = new StringBuilder(width)
    for (i <- 0 until width) i match {
      case LeftPos => line += '/'
      case RightPos => line += '\\'
      case _ => line += ' '
    }
    line.toString
  }

  def create: String = {
    val module = new StringBuilder(height * width)
    val rightPos = width / 2
    val leftPos = rightPos - 1
    for (i <- 0 until height) module ++= createLine(leftPos - i, rightPos + i)
    module.toString
  }

}

object TerminalModule {
  def apply(height: Int, width: Int, code: String): TerminalModule = new TerminalModule(height, width, code)
}

