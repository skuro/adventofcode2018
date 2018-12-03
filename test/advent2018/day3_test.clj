(ns advent2018.day3-test
  (:require [advent2018.day3 :as sut]
            [clojure.test :refer :all]))

(deftest square-coords-test
  (let [claim [42 1 3 4 4]] ;; <- [id pad-left pad-top length height]
    (is (= [[1 3] [1 4] [1 5] [1 6]
            [2 3] [2 4] [2 5] [2 6]
            [3 3] [3 4] [3 5] [3 6]
            [4 3] [4 4] [4 5] [4 6]]
           (sut/square-coords claim)))))

(deftest total-overlapping-test
  (let [claims (map sut/parse-claim ["#1 @ 1,3: 4x4"
                                     "#2 @ 3,1: 4x4"
                                     "#3 @ 5,5: 2x2"])]
    (is (= 4 (sut/total-overlapping claims)))))

(deftest find-unique-claim-test
  (let [claims (map sut/parse-claim ["#1 @ 1,3: 4x4"
                                     "#2 @ 3,1: 4x4"
                                     "#3 @ 5,5: 2x2"])]
    (is (= 3 (sut/find-unique-claim* claims)))))
