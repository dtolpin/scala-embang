package embang.scala {
  import clojure.java.api._
  import clojure.core._
  import scala.collection.JavaConverters._ 
  import scala.util.parsing.json._
  import scala.collection.immutable.ListMap

// Scala interface to probabilistic programming language m!
// Synopsis:
//
//    import embang.scala._
//
//    val X = draw from (query = "HMM")
//    for (i <- 1 to 100) {
//      val x = X.next
//      println(x.predicts, x.logWeight)
//    }
//
// predicts is a ListMap from predict identifiers to predict values.

  class Sample(val predicts: Map[String, _], val logWeight: Double)

  object draw {
    // Setup Clojure runtime and require facility.
    private val require = Clojure.`var`("clojure.core", "require")
    private val apply = Clojure.`var`("clojure.core", "apply")

    // Load doquery function.
    private val doquery = { 
      require.invoke(Clojure.read("embang.json"))
      Clojure.`var`("embang.json", "doquery")
    }

    private[scala] def asSample(sample: Option[_]): Sample = {
      sample match {
        case Some(s: Map[String, Any]) => 
          new Sample(ListMap()
                       ++ s("predicts").asInstanceOf[List[List[Any]]]
                            .map(l => (l(0).toString, l(1))),
                     s("log-weight").asInstanceOf[Double])
        case _ => new Sample(ListMap(), -1./0.)
      }
    }

    def from (algorithm: String = ":lmh",
              query: String, value: String = "nil",
              options: String = "") = {

      // Parse the query namespace and name, and load the query.
      val (namespace, name) = 
        query split "/" match {
          case Array(namespace, name) => (namespace, name)
          case _ => (query, query)
        }
      require.invoke(Clojure.read(namespace))

      // Fetch a lazy sequence of samples ...
      val samples = apply.invoke(doquery,
        Clojure.read(algorithm), 
        Clojure.`var`(Clojure.read(namespace), Clojure.read(name)),
        Clojure.read(value),
        Clojure.read("["+options+"]")
      ).asInstanceOf[java.util.Iterator[_]]

      // ... parsing JSON strings into JSON structures.
      for(sample <- samples.asScala)
        yield asSample(JSON.parseFull(sample.asInstanceOf[String]))
    }

  }
}
