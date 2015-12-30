(ns rdfsplit.core
  (:require
    [clojure.string :as string]))

(defn rdfsplit [files { :keys [output]
                        :or {output "."}
                        :as options } ]
  (println "rdfsplit" "--output" output files)
  (println "........")

)
