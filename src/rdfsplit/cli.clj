(ns rdfsplit.cli
  (:require
    [rdfsplit.core :refer :all]
    [clojure.string :as string]
    [clojure.tools.cli :refer [parse-opts]])
  (:gen-class))

(def cli-options
  [["-o" "--output OUTPUTDIR" "Output directory (default is current directory)" :default (default-options :output)]
   ["-r" "--recursive" "Recurse into subdirectories"]
   ["-f" "--force" "Overwrite any existing output files. Make output directory if missing."]
   ["-v" "--verbose" "Verbose log output"]
   ["-n" "--max-quads QUADS" "Maximum number of quads per file" :default (default-options :max_quads)]
   ["-b" "--max-bytes BYTES" "Rough maximum file size in bytes" :default (default-options :max_bytes)]
   [nil "--no-compress" "Do not compress files using gzip"]
   ["-g" "--graph URI" "Assign graph URI for triples in the default graph"]
   [nil "--force-graph" "Assign provided --graph for all quads"]
   ["-h" "--help"]])

(defn usage [options-summary]
  (->> ["Split RDF files into reasonably sized N-Quads files."
        ""
        "Usage: rdfsplit [options] FILE .."
        "   or: lein run -- [options] FILE .."
        ""
        "Options:"
        options-summary
        ""]
       (string/join \newline)))

(defn error-msg [errors]
  (str "The following errors occurred while parsing your command:\n\n"
       (string/join \newline errors)))

(defn exit
  ([status]
    (exit status nil))
  ([status msg]
    (if msg
      (if (> 0 status)
        (binding [*out* *err*] (println msg))
        (println msg)))
    (System/exit status)))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (let [{:keys [options arguments errors summary]} (parse-opts args cli-options)]
    (cond
      (:help options) (exit 0 (usage summary))
      (empty? arguments) (exit 1 (usage summary))
      errors (exit 1 (error-msg errors)))
    (try
      (rdfsplit arguments options)
      (exit 0)
    (catch Exception e
      (if (:verbose options)
        (.printStackTrace e))
      (exit 2 (.toString e))))))
