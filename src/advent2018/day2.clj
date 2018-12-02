(ns advent2018.day2
  (:require [clojure.set :as set]
            [clojure.java.io :as io]))

;;;;;;;;;;;;;;;; yak shaving ;;;;;;;;;;;;;;;
(def read-input (comp line-seq io/reader io/resource))

;;;;;;;;;;;;;;;; part 1 ;;;;;;;;;;;;;;;
(defn id-scores
  "Returns a vector of [<1 or 0> <1 or 0>] indicating whether there's a letter appearing twice and thrice,
  respectively"
  [id]
  (let [freqs (into #{} (vals (frequencies id)))
        has?  (fn [freq]
                (if freq
                  1
                  0))]
    [(has? (freqs 2))
     (has? (freqs 3))]))

(defn checksum
  "Calculates the checksum of IDs by multiplying the number of those having at least one letter appearing twice
  by the number of those having at least one letter appearing thrice"
  [ids]
  (let [scores (map id-scores ids)
        counts (reduce (fn [acc elem] (mapv + acc elem)) scores)]
    (apply * counts)))

(defn solve-one [path]
  (-> (read-input path)
      (checksum)))

;;;;;;;;;;;;;;;; part 2 ;;;;;;;;;;;;;;;
(defn distance-one-nth
  "When str1 and str2 differ only by a character, returns the index of such different character, false otherwise"
  [str1 str2]
  (loop [[h1 & str1] str1
         [h2 & str2] str2
         count       0
         diff-nth    nil
         diff        0]
    (cond
      (nil? h1)
      (and (= 1 diff)
           diff-nth)

      (< 1 diff)
      false

      (= h1 h2)
      (recur str1 str2 (inc count) diff-nth diff)

      :else
      (recur str1 str2 (inc count) count (inc diff)))))

(defn find-boxes
  "Returns the first two IDs that differ by only one character"
  [ids]
  (first (for [x  ids
               y  ids
               :let [d1 (distance-one-nth x y)]
               :when (and d1
                          (not= x y))]
           [x y d1])))

(defn common-letters
  "Given a [box1 box2 diff-nth] descriptor of two ids with one different character, returns the string of the
  common letters"
  [[box1 _ diff-nth]]
  (str (subs box1 0 diff-nth)
       (subs box1 (inc diff-nth))))

(defn solve-two [path]
  (-> (read-input path)
      find-boxes
      common-letters))
