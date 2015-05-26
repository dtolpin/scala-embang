name := "scala-embang"

// Resolvers

resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"

resolvers += "Sonatype OSS Releases" at "https://oss.sonatype.org/service/local/staging/deploy/maven2"

resolvers += "clojars" at "https://clojars.org/repo"

// Dependencies

libraryDependencies += "net.offtopia" % "embang" % "1.0.0-SNAPSHOT"
