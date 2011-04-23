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
	(is (hard? "CBABCBA"))
	(is (not (hard-at-last-index? "ABCDABCD"))))

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
	(is (= (promote-seq-next [\A \C] [\A \B \C]) [\B]))
	(is (= (promote-seq-next [\C \C] [\A \B \C]) [])))

(deftest permutated-lazy-sed
	(is (= (take 10 (permutated-lazy-seq [\A] [\A \B])) (map vec ["A" "AB" "ABA" "B" "BA" "BAB"])))
	(is (= (take 10 (permutated-lazy-seq [\A \B])) (map vec ["A" "AB" "ABA" "B" "BA" "BAB"]))))

(deftest get-hard-sequence
	(is (= (hard-seq 1 2) (vec "A")))
	(is (= (hard-seq 3 2) (vec "ABA")))
	(is (= (hard-seq -100 2) nil))
	(is (= (hard-seq 10 2) nil))
	(is (= (hard-seq 7 3) (vec "ABACABA"))))

(deftest sequence-pretty-printing
	(is (= (seq-pretty-print "ABCDEF") "ABCD EF"))
	(is (= (seq-pretty-print "ABCDEFABCDEFABCDEF") "ABCD EFAB CDEF ABCD EF"))
	(is (= (seq-pretty-print "") ""))
	(is (= (seq-pretty-print (str "ABCDEFABCDEFABCDEFABCDEFABCDE" ; 7 groups
								  "ABCDEFABCDEFABCDEFABCDEFABCDE"
								  "ABCDEFABCDEFABCDEFABCDEFABC"))
			"ABCD EFAB CDEF ABCD EFAB CDEF ABCD EABC DEFA BCDE FABC DEFA BCDE FABC DEAB CDEF\nABCD EFAB CDEF ABCD EFAB C"))
	)

(run-tests)