(ns rdfsplit.core-test
  (:require [clojure.test :refer :all]
            [rdfsplit.core :refer :all]))

(deftest a-test

  (testing "Splitting no files"
    (rdfsplit [] {:output "/tmp"})
    (is (= 0 1))))
