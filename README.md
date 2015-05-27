# scala-embang

Scala interop for [__m!__](https://bitbucket.org/dtolpin/embang).

How to:

    import embang.scala._
    
    val X = draw from (query = "HMM")
    for (i <- 1 to 100) {
        val x = X.next
        println(x.predicts, x.logWeight)
    }

as well as anything more complicated than this. 
