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

(deftest easy-sequence-at-index
	(is (easy-at-index? "BB" 1 2))
	(is (easy-at-index? "BB" 1))
	(is (not (easy-at-index? "BC" 1 2)))
	(is (not (easy-at-index? "BC" 1)))
	(is (not (easy-at-index? "BC" 0 1)))
	(is (not (easy-at-index? "BB" 0 1)))
	(is (easy-at-index? "ABAB" 3 4))
	(is (easy-at-index? "ABCDACABCAB" 10 11))
	(is (not (easy-at-index? "ABCDACABCAB" 9 10)))
	(is (not (easy-at-index? "ABCDABCD" 3 4)))
	(is (easy-at-index? "ABCDABCD" 7 8)))

(deftest easy-corner-cases
	(is (easy? "bB"))
	(is (not (easy-at-index? "" 0))))

(run-tests)