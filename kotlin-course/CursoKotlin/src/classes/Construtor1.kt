package classes

data class Movie (val name: String, val year :Int, val genre: String = "Drama")

fun main(args: Array<String>) {
    val movie = Movie("The Godfather", 1972)
    println("The ${movie.genre} ${movie.name} was released in ${movie.year}.")

    val res = when (movie.genre){
        "Comedy" -> 1
        "Drama" -> 2
        "Terror" -> 3
        else -> 0
    }
    println(res)
}