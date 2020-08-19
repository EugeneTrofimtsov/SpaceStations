package spaceX.blocks

class HumanModule(override val height: Int, override val width: Int, override val code: String) extends Block {
  require(height >= 4 && width >= 4, "too small module")

  val moduleType: ModuleType = HM

  private def createLine(leftWall: Char = '|', inside: Char = ' ', rightWall: Char = '|'): String = {
    val line = new StringBuilder(width)
    line += leftWall
    for (_ <- 1 until width - 1) line += inside
    line += rightWall
    line.toString
  }

  def create: String = {
    val module = new StringBuilder(height * width)
    module ++= createLine(leftWall = ' ', inside = '-', rightWall = ' ')
    module ++= createLine(leftWall = '/', rightWall = '\\')
    for (_ <- 2 until height - 2) module ++= createLine()
    module ++= createLine(leftWall = '\\', rightWall = '/')
    module ++= createLine(leftWall = ' ', inside = '-', rightWall = ' ')
    module.toString
  }

}

object HumanModule {
  def apply(height: Int, width: Int, code: String): HumanModule = new HumanModule(height, width, code)
}

