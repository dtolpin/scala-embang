# scala-embang

Scala interop for [__m!__](https://bitbucket.org/dtolpin/embang).

How to:

    import embang.scala._
    
    val X = draw from (query = "HMM")
    for (i <- 1 to 100) {
        val x = X.next
        println(x.predicts, x.logWeight)
    }

Or, if unweighted samples are preferred:

    val X = equalize(draw from (query = "HMM"))
    for (i <- 1 to 100) {
        val x = X.next
        println(x.predicts)
    }

as well as anything more complicated than this. The query
is an __m!__ program, which must be in the class path, the
rules are the same as for [__m!__](https://bitbucket.org/dtolpin/embang).
