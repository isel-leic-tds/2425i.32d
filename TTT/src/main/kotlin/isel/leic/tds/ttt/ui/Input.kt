package isel.leic.tds.ttt.ui

data class LineCommand(val name: String, val args: List<String>)

tailrec fun readCommand(): LineCommand {
    print("> ")
    val words = readln().trim().uppercase().split(' ')
    return if (words.isNotEmpty() && words.first().isNotBlank())
        LineCommand(words[0], words.drop(1))
    else readCommand()
}