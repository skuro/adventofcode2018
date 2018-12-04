(ns advent2018.day4
  (:require [clojure.java.io :as io])
  (:import [java.text SimpleDateFormat]
           [java.util Date]))

;;;;;;;;;;;; yak shaving ;;;;;;;;;;;;
(defn read-input [path]
  (line-seq (io/reader (io/resource path))))

(def sdf (SimpleDateFormat. "yyyy-MM-dd HH:mm"))

(defn parse-entry [entry]
  (let [[_ date txt] (re-find #"\[(.*)\] (.*)" entry)]
    (cond (pos? (.indexOf txt "asleep"))
          {:type :asleep
           :time (.parse sdf date)}

          (pos? (.indexOf txt "up"))
          {:type :up
           :time (.parse sdf date)}

          :else
          (let [[_ guard] (re-find #"Guard \#(\d+).*" txt)]
            {:type :guard
             :time (.parse sdf date)
             :guard (read-string guard)}))))

;;;;;;;;;;;; part one ;;;;;;;;;;;;
(defn asleep-minutes [[{start :time} {end :time}]]
  (let [start-mins (.getMinutes start)
        end-mins   (.getMinutes end)]
    (range start-mins end-mins)))

(defn asleep-minutes-per-turn [log-entries]
  (let [[guard & asleep-and-wakeups] log-entries
        starts-and-ends              (partition 2 asleep-and-wakeups)
        minutes-asleep               (mapcat asleep-minutes starts-and-ends)]
    (map (partial vector (:guard guard)) minutes-asleep)))

(defn all-asleep-minutes [sorted-log]
  (let [current-guard (atom nil)
        partitioner   (fn [{:keys [guard]}]
                        (if guard
                          (reset! current-guard guard)
                          @current-guard))
        logs-per-turn (partition-by partitioner sorted-log)]
    (mapcat asleep-minutes-per-turn logs-per-turn)))

(defn most-sleepy [asleep-minutes]
  (let [by-guard     (partition-by first (sort-by first asleep-minutes))
        guard-totals (map (juxt ffirst count) by-guard)]
    (ffirst (sort-by second > guard-totals))))

(defn most-sleepy-minute [log]
  (let [sorted-log   (sort-by :time log)
        asleep-mins  (all-asleep-minutes sorted-log)
        sleepy-guard (most-sleepy asleep-mins)]
    (->> (filter (comp #{sleepy-guard} first) asleep-mins)
         (map second)
         (frequencies)
         (sort-by second >)
         (ffirst)
         (vector sleepy-guard))))

(defn solve-one [path]
  (let [log            (map parse-entry (read-input path))
        [guard minute] (most-sleepy-minute log)]
    (println guard minute)
    (* guard minute)))

;;;;;;;;;;;; part two ;;;;;;;;;;;;
(defn most-asleep-in-minute [log]
  (let [sorted-log  (sort-by :time log)
        asleep-mins (all-asleep-minutes sorted-log)]
    (ffirst (sort-by second > (frequencies asleep-mins)))))

(defn solve-two [path]
  (let [log            (map parse-entry (read-input path))
        [guard minute] (most-asleep-in-minute log)]
    (println guard minute)
    (* guard minute)))
