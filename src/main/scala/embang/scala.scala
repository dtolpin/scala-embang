package embang.scala {
  import clojure.java.api._
  import clojure.core._
  import scala.collection.JavaConverters._ 

  object sample {
    private val require = Clojure.`var`("clojure.core", "require")
    private val doquery = { 
      require.invoke(Clojure.read("embang.trap"))
      require.invoke(Clojure.read("embang.json"))
      Clojure.`var`("embang.json", "doquery")
    }
    def from (algorithm: String, query: String, value: String) = {

      val (namespace, name) = 
        query split "/" match {
          case Array(namespace, name) => (namespace, name)
          case _ => (query, query)
        }
      require.invoke(Clojure.read(namespace))

      val samples = doquery.invoke(
        Clojure.read(algorithm), 
        Clojure.`var`(Clojure.read(namespace), Clojure.read(name)),
        Clojure.read(value)
      ).asInstanceOf[java.util.Iterator[_]]

      samples.asScala
    }

  }
}
