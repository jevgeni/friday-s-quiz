(ns fridaysquiz.test.krypton_factor.core
	(:use [fridaysquiz.krypton_factor.core] :reload-all)
    (:use [clojure.test]))

(deftest easy-sequence
    (is (easy? "BB"))
    (is (easy? "ABCDACABCAB"))
    (is (easy? "ABCDABCD")))

(deftest hard-sequence
	(is (not (easy? "D")))
	(is (not (easy? "DC")))
	(is (not (easy? "ABDAB")))
	(is (not (easy? "CBABCBA"))))