(ns fridaysquiz.test.krypton_factor.core
	(:use [fridaysquiz.krypton_factor.core] :reload-all)
    (:use [clojure.test]))

(deftest easy-sequence
    (is (easy? "BB"))
    (is (easy? "ABCDACABCAB"))
    (is (easy? "ABCDABCD")))

(deftest hard-sequence
	(is (hard? "D"))
	(is (hard? "DC"))
	(is (hard? "ABDAB"))
	(is (hard? "CBABCBA")))

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

(deftest permutate-sequence
	(is (= (promote-seq-down [\B \B] [\B \C]) [\B \B \B]))
	(is (= (promote-seq-down [\B \B] [\A \B \C]) [\B \B \A]))
	(is (= (promote-seq-next [\A \A] [\A \B \C]) [\A \B]))
	(is (= (promote-seq-next [\A \B] [\A \B \C]) [\A \C]))
	;(is (= (extend-seq-next [\A \C] [\A \B \C]) [\B \A]))
	;(is (= (extend-seq-next [] [\A \B \C]) [\A]))
	)



(run-tests)