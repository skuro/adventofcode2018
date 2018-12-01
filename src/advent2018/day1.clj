(ns advent2018.day1
  "Solve https://adventofcode.com/2018/day/1"
  (:require [clojure.java.io :as io]))

;;;;;;;;;;;;; yak shaving ;;;;;;;;;;;;;
(def read-input (comp io/reader io/resource))

(defn parse-input [input-file]
  (map read-string (line-seq input-file)))

;;;;;;;;;;;;; part one ;;;;;;;;;;;;;
(defn apply-freq-diffs [diffs]
  (reduce + 0 diffs))

(defn solve-part-one [file-path]
  (-> (read-input file-path)
      parse-input
      apply-freq-diffs))

;;;;;;;;;;;;; part two ;;;;;;;;;;;;;
(defn find-first-dupe [diffs]
  (loop [[freq & freqs] (reductions + 0 (cycle diffs))
         seen?  #{}]
    (if (seen? freq)
      freq
      (recur freqs
             (conj seen? freq)))))

(defn solve-part-two [file-path]
  (-> (read-input file-path)
      parse-input
      find-first-dupe))

(comment
  (solve-part-one "day1.txt")
  (solve-part-two "day1.txt"))
