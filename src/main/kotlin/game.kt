import java.io.File

object game {
    @JvmStatic
    fun main(args: Array<String>): Unit {
        if(args.isNotEmpty()) {
            args[0].let{ configFilePath ->
                readAndLoadFile(configFilePath)?.let { configuration ->
                    Board(configuration).play()
                }
            }
        } else {
            println("Please Enter Valid configuration Path")
        }
    }
    private fun readAndLoadFile(configFilePath: String): Configuration? = File(configFilePath).let { file ->
        if (file.exists() && file.isFile && file.canRead()) {
            try {
                Mapper.mapperDash.readValue<Configuration>(
                    File(configFilePath),
                    Configuration::class.java)
            } catch (e: Exception) {
                println(e.message)
                println("Error while parsing file")
                null
            }
        } else {
            println("File does not exist at given location")
            null
        }
    }
}