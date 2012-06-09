package models
import java.util.UUID
import java.io._
import scala.sys.process._
import scalax.io._

class Disassembler {
  val tmpDir = "data/"

  def disassemble(code:String):String = {
    val logger = new Logger()
    var error = false
    var message = ""
    //generate UUID
    val rawId = "t" + UUID.randomUUID().toString()
    val id = rawId.replaceAll("-", "")

    //create file in tmpDir
    val fw = new FileWriter(tmpDir + id + ".java")

    //write code out to that file
    try {
      fw.write("public class " + id + " { public static void test(){")
      fw.write(code)
      fw.write("}}")
      fw.flush()
    }
    catch {
      case e: Exception =>
       message += "Unable to create source.<br>"
       error = true
    }
    finally {
      fw.close()
    }
    if (error) return message

    //run compiler
    try {
      val compilationCommand = "javac " + tmpDir + id + ".java"
      compilationCommand !! logger.log
    }
    catch {
      case e: Exception =>
       message += clean(logger.err)
       error = true
       quietRemoveFile(id)
       return message
    }

    var decomp = "An unknown error has occurred"
    try {
      val decompCommand = "javap -c " + tmpDir + id
      //decomp = decomp.replaceAll("Compiled from " + id, "")
      //decomp = decomp.replaceAll("\u007D", "\u007D\u007D")
      //decomp = decomp.replaceAll("\u007B", "\u007B\u007B")
      decomp = decompCommand !! logger.log
      decomp = clean(decomp)
    }
    catch {
      case e: Exception =>
       message += clean(logger.err)
       error = true
       quietRemoveFile(id)
       return message
    }

    //remove
    try {
      removeFile(id)
    }
    catch {
      case e: Exception =>
       message += "Unable to cleanup.<br>"
       error = true
       return message
    }

    if (error) return message
    else return decomp
  }
  
  def clean(str:StringBuilder): String = {
    clean(str.toString())
  }

  def clean(str:String):String = {
    var decomp = str.replaceAll("\\{", "{{")
    decomp = decomp.replaceAll("\\}", "}}")
    decomp = decomp.replaceAll("\"", "'")
    decomp = decomp.replaceAll("\n", "<br>")
    decomp = decomp.replaceAll("\t", " ")
    decomp = decomp.replaceAll("\u005E", " ")
    decomp = decomp.replaceAll("\u2038", " ")
    decomp = decomp.replaceAll("\\^", " ")
    decomp = decomp.replaceAll("\\s\\s\\s\\s\\s+", "<br>")
    decomp = decomp.replaceAll("\u001A", "")
    decomp = decomp.replaceAll("^\\s+", "")
    decomp
  }

  def quietRemoveFile(id:String) {
    try {
      val sourceFile = new File(tmpDir + id + ".java")
      sourceFile.delete()

      val classFile = new File(tmpDir + id + ".class")
      classFile.delete()
    }
    catch {
      case e: Exception => System.out.println("Couldn't remove file")
    }
  }

  def removeFile(id:String) {
    val sourceFile = new File(tmpDir + id + ".java")
    sourceFile.delete()

    val classFile = new File(tmpDir + id + ".class")
    classFile.delete()
  }

}
