(defproject rdfsplit "0.1.0-SNAPSHOT"
  :description "Split large RDF files into reasonably sized N-Quads files"
  :url "https://github.com/stain/rdfsplit"
  :license {:name "Apache License, version 2.0"
            :url "http://www.apache.org/licenses/LICENSE-2.0"}
  :dependencies [
    [org.clojure/clojure "1.7.0"]
    [org.clojure/tools.cli "0.3.3"]
    [org.apache.jena/jena-arq "3.0.0"]
    [org.tobereplaced/nio.file "0.4.0"]
  ]
  :main ^:skip-aot rdfsplit.cli
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
