(ns advent2018.day1
  (:require [clojure.java.io :as io]))

(def read-input (comp io/reader io/resource))

(defn parse-input [input-file]
  (map read-string (line-seq input-file)))

(defn apply-freq-diffs [diffs]
  (reduce + 0 diffs))

(defn find-first-dupe [diffs]
  (loop [[freq & freqs] (reductions + 0 (cycle diffs))
         seen?  #{}]
    (cond
      (seen? freq)
      freq

      (seq freqs)
      (recur freqs
             (conj seen? freq))

      :else
      (println "nope"))))

(defn solve-part-one [file-path]
  (-> (read-input file-path)
      parse-input
      apply-freq-diffs))

(defn solve-part-two [file-path]
  (-> (read-input file-path)
      parse-input
      find-first-dupe))

(comment
  (solve-part-one "day1.txt")
  (solve-part-two "day1.txt"))
