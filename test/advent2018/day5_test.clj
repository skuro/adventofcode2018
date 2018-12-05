(ns advent2018.day5-test
  (:require [advent2018.day5 :as sut]
            [clojure.test :refer :all]))

(defn reaction-str [react]
  (apply str (reverse react)))

(deftest chain-reaction-test
  (is (= 10 (count (sut/chain-reaction "dabAcCaCBAcCcaDA"))))
  (is (= "dabCBAcaDA" (reaction-str(sut/chain-reaction "dabAcCaCBAcCcaDA"))))
  (is (empty? (sut/chain-reaction "aA")))
  (is (empty? (reaction-str (sut/chain-reaction "abBA"))))
  (is (= "abAB" (reaction-str (sut/chain-reaction "abAB"))))
  (is (= "aabAAB" (reaction-str (sut/chain-reaction "aabAAB")))))

(deftest try-reactions-test
  (is (= "dbCBcD"   (reaction-str (sut/try-reaction \a "dabAcCaCBAcCcaDA"))))
  (is (= "daCAcaDA" (reaction-str (sut/try-reaction \b "dabAcCaCBAcCcaDA"))))
  (is (= "daDA"     (reaction-str (sut/try-reaction \c "dabAcCaCBAcCcaDA"))))
  (is (= "abCBAc"   (reaction-str (sut/try-reaction \d "dabAcCaCBAcCcaDA"))))
  (is (= (sort ["dbCBcD" "daCAcaDA" "daDA" "abCBAc"])
         (sort (map reaction-str (sut/try-reactions (seq "dabAcCaCBAcCcaDA")))))))
