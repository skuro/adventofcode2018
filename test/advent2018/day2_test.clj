(ns advent2018.day2-test
  (:require [advent2018.day2 :as sut]
            [clojure.test :refer :all]))

(deftest solve-test
  (is (= 7105 (sut/solve "day2.txt"))))

(deftest distance-one-nth-test
  (are [str1 str2 expected] (= expected (sut/distance-one-nth str1 str2))
    "test" "fest" 0
    "test" "test" false
    "test" "tttt" false
    "test" "tesk" 3))

(deftest find-boxes-test
  (let [boxes ["test"
               "aaaa"
               "bbbb"
               "cccc"
               "tesk"]]
    (is (= ["test" "tesk" 3]
           (sut/find-boxes boxes)))))

(deftest common-letters-test
  (let [boxes ["test"
               "aaaa"
               "bbbb"
               "cccc"
               "tesk"]]
    (is (= "tes"
           (sut/common-letters (sut/find-boxes boxes))))))
