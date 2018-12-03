(ns advent2018.day3
  (:require [clojure.java.io :as io]
            [clojure.set :as set]))

;;;;;;;;;;;;; yak shaving ;;;;;;;;;;;;;;
(defn read-input [path]
  (line-seq (io/reader (io/resource path))))

(defn parse-claim [claim]
  (let [format                            #"#(\d+) @ (\d+),(\d+): (\d+)x(\d+)"
        [id pad-left pad-top width height] (map #(Integer/parseInt %)
                                                (rest (re-find format claim)))]
    [id pad-left pad-top width height]))

(defn parse-input [lines]
  (map parse-claim lines))

;;;;;;;;;;;;; part one ;;;;;;;;;;;;;;
(defn square-coords [[_ pad-left pad-top width height]]
  (for [x (range width)
        y (range height)]
    [(+ x pad-left) (+ y pad-top)]))

(defn total-overlapping [claims]
  (->> (mapcat square-coords claims)
       (frequencies)
       (filter (fn [[_ f]] (< 1 f)))
       (count)))

(defn solve-one [path]
  (-> path
      read-input
      parse-input
      total-overlapping))

;;;;;;;;;;;;; part two ;;;;;;;;;;;;;;
(def square-coords* (memoize square-coords))

(defn find-unique-claim [claims]
  (let [onesies (->> claims
                     (mapcat (comp set square-coords*))
                     (frequencies)
                     (filter (comp #{1} val))
                     (keys)
                     (set))
        only-onesies? (fn [claim]
                        (let [coords (set (square-coords* claim))]
                          (set/subset? coords onesies)))]
    (ffirst (filter only-onesies? claims))))

(defn solve-two [path]
  (-> path
      read-input
      parse-input
      find-unique-claim))
