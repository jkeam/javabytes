package models
import sys.process._

class Logger {
  val out = new StringBuilder
  val err = new StringBuilder

  val log = ProcessLogger(
    (o: String) => out.append(o),
    (e: String) => err.append(e)
  )

  def getLog(): ProcessLogger = {
    return log
  }
}
