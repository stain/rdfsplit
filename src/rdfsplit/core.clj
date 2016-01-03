(ns rdfsplit.core
  (:import
  [java.io BufferedOutputStream]
  [java.nio.file Paths Files OpenOption StandardOpenOption]
  [org.apache.jena.graph Node Triple]
  [org.apache.jena.riot RDFDataMgr RDFFormat]
  [org.apache.jena.riot.system StreamRDF StreamRDFWriter])
  (:require
    [clojure.string :as string]))

;RDFFormat fmt
;StreamRDFWriter.getWriterStream(output, fmt)

(defn get-format [options]
  RDFFormat/NQUADS)

(def counter (atom 0))

(defn next-from-counter []
  (swap! counter inc))

(defn paths-get [pathname & pathnames]
  (Paths/get pathname (into-array pathnames)))

(defn next-filename [options]
  ;; TODO: Support other extensions like .ttl and .nq.gz
  (paths-get (:output options) (str (next-from-counter) ".nq")))

; TODO: is 5 MB buffer too much?
(def output-buffer-size-bytes (* 5 1024 1024))

(defn make-output-stream [options]
  (let [filename (next-filename options)
        outstream (BufferedOutputStream.
          (Files/newOutputStream filename
            (into-array OpenOption
              (if (:force options)
                [StandardOpenOption/WRITE StandardOpenOption/CREATE StandardOpenOption/TRUNCATE_EXISTING]
                [StandardOpenOption/WRITE StandardOpenOption/CREATE_NEW])))
          output-buffer-size-bytes)]
    (if (:verbose options)
      (println (.toString filename)))
    outstream))

(defn create-writer-stream [options]
  (StreamRDFWriter/getWriterStream (make-output-stream options) (get-format options)))

(defn get-writer-stream [options]
  (create-writer-stream options))


(defn- parse-rdfstream [options]
  (let [writer-stream (get-writer-stream options)]
  ;; TODO: How to keep a long-living StreamRDF instance that can swap
  ;; underlying writer-stream?
    (proxy [StreamRDF] []
      (base [base] nil)
      (finish [] nil)
      (prefix [prefix iri] nil)
      (start [] nil)
      (triple [triple]
        (println triple))
      (quad [quad]
        (println quad)))
    (get-writer-stream options)))

(defn parse [options streamrdf url]
  (RDFDataMgr/parse streamrdf url))

(defn rdfsplit [files { :keys [output]
                        :or {output "."}
                        :as options } ]
  (if (:verbose options)
    (println "rdfsplit" "--output" output files))
  (doall (map (partial parse options (parse-rdfstream options)) files))
  (if (:verbose options)
    (println "Done.")))
