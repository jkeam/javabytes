package models
import java.util.UUID
import java.io._
import java.nio.file._
import java.util.regex._
import scala.sys.process._
import scalax.io._

class Disassembler {
  val tmpDir = "./data/"

  def disassemble(code:String):String = {
    val logger = new Logger()
    var error = false
    var message = ""

    //generate UUID
    val rawId = "t" + UUID.randomUUID().toString()
    val id = rawId.replaceAll("-", "").replaceAll("/", "").replaceAll("\\\\", "").replaceAll(":", "")

    //create tmpDir
    val userTmpDirName = tmpDir + id
    val userTmpDir = createDir(userTmpDirName)

    //try and extract name of class
    val className = extractClassname(code)
    if (className == "" || className == null) {
      message += clean(logger.err, userTmpDirName)
      error = true
      removeDir(userTmpDir)
      return message
    }

    //create tmp file
    val javaFilename = userTmpDirName + "/" + className + ".java"
    val classFilename = userTmpDirName + "/" + className + ".class"
    val fw = new FileWriter(javaFilename)

    //write code out to that file
    try {
      fw.write(code)
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
      val compilationCommand = "javac " + javaFilename
      compilationCommand !! logger.log
    }
    catch {
      case e: Exception =>
       message += clean(logger.err, userTmpDirName)
       error = true
       quietRemoveFile(javaFilename, classFilename, userTmpDir)
       return message
    }

    //dissassemble
    var decomp = "An unknown error has occurred"
    try {
      val decompCommand = "javap -c " + classFilename
      decomp = decompCommand !! logger.log
      decomp = clean(decomp, userTmpDirName)
    }
    catch {
      case e: Exception =>
       message += clean(logger.err, userTmpDirName)
       error = true
       quietRemoveFile(javaFilename, classFilename, userTmpDir)
       return message
    }

    //remove all junk files created
    try {
      removeFile(javaFilename, classFilename, userTmpDir)
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

  def createDir(str:String):Path = {
    val folderPath = Paths.get(str)
    Files.createDirectories(folderPath)
  }

  def removeDir(userTmpDir:Path) = {
    Files.delete(userTmpDir)
  }
  
  def clean(str:StringBuilder, dirPrefix:String):String = {
    clean(str.toString(), dirPrefix)
  }

  def clean(str:String, dirPrefix:String):String = {
    var decomp = str.replaceAll("\\{", "{{")
    decomp = decomp.replaceAll(dirPrefix+ "/", "")
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

  def quietRemoveFile(filename:String, classname:String, userTmpDir:Path) = {
    try {
      val sourceFile = new File(filename)
      sourceFile.delete()

      val classFile = new File(classname)
      classFile.delete()

      Files.delete(userTmpDir)
    }
    catch {
      case e: Exception => System.out.println("Couldn't remove file")
    }
  }

  def removeFile(filename:String, classname:String, userTmpDir:Path) = {
    val sourceFile = new File(filename)
    sourceFile.delete()

    val classFile = new File(classname)
    classFile.delete()

    Files.delete(userTmpDir)
  }

  def extractClassname(code:String):String = {
    val pattern = Pattern.compile("\\s*(public|private)\\s+class\\s+(\\w+)\\s+((extends\\s+\\w+)|(implements\\s+\\w+( ,\\w+)*))?\\s*\\{");
    val m = pattern.matcher(code)

    if (m.find()) {
      m.group(2)
    } else {
      null
    }
  }
}
