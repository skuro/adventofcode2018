(ns advent2018.day5
  (:require [clojure.string :as str]
            [clojure.java.io :as io]))

;;;;;;;;;;; yak shaving ;;;;;;;;;;;
(defn read-input [path]
  (butlast (slurp (io/resource path))))

;;;;;;;;;;; part one ;;;;;;;;;;;
(defn react? [x y]
  (when (and x y)
    (= 32 (Math/abs (- (int x)
                       (int y))))))

(defn chain-reaction [units]
  (loop [[unit & units]     units
         seen               ()]
    (if (nil? unit)
      seen
      (if (react? unit (first seen))
        (recur units
               (rest seen))
        (recur units
               (conj seen unit))))))

(defn solve-one [path]
  (count (chain-reaction (read-input path))))

;;;;;;;;;;; part two ;;;;;;;;;;;
(defn try-reaction [avoid-unit units]
  (let [avoid-pattern #{avoid-unit (first (str/upper-case avoid-unit))}]
    (chain-reaction (remove avoid-pattern units))))

(defn try-reactions [units]
  (let [letters (keys (frequencies (str/lower-case (apply str units))))]
    (map #(try-reaction % units)
         letters)))

(defn smallest-reaction [units]
  (apply min (map count (try-reactions units))))

(defn solve-two [path]
  (smallest-reaction (read-input path)))
