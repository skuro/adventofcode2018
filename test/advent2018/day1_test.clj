(ns advent2018.day1-test
  (:require [advent2018.day1 :as sut]
            [clojure.test :refer :all]))

(deftest test-solve-part-one
  (is (= 592 (sut/solve-part-one "day1.txt"))))

(deftest test-solve-part-two
  (is (= 241 (sut/solve-part-two "day1.txt"))))
