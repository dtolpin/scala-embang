package embang.scala {
  import clojure.java.api._
  import clojure.core._
  import scala.collection.JavaConverters._ 
  import scala.util.parsing.json._

  object sample {
    // Setup Clojure runtime and require facility.
    private val require = Clojure.`var`("clojure.core", "require")
    private val apply = Clojure.`var`("clojure.core", "apply")

    // Load doquery function.
    private val doquery = { 
      require.invoke(Clojure.read("embang.trap"))
      require.invoke(Clojure.read("embang.json"))
      Clojure.`var`("embang.json", "doquery")
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
        Clojure.read(options)
      ).asInstanceOf[java.util.Iterator[_]]

      // ... parsing JSON strings into JSON structures.
      for(sample <- samples.asScala)
        yield JSON.parseFull(sample.asInstanceOf[String])
    }

  }
}
