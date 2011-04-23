(ns fridaysquiz.krypton_factor.core
	(:use [clojure.string :only (upper-case)]))

(defn easy-at-index?
	"Checks the occurence of two adjoing identical subsequences. The first subsequence starts at start-index
	(inclusive) and ends at end-index (exclusive).
	The second sequence is adjoining the first sequence on the left side. If the subsequences do not match,
	then the check is continued for first subsequence of different size: the first sequence's start-index is
	decreased by one, second sequence is recalculated and compared. The recursion ends when there are not
	possible to calculate adjoined sequence anymore."
	([l start-index]
		(easy-at-index? l start-index (inc start-index)))
	([l start-index end-index]
		(let [pivot-size (- end-index start-index)
		      adj-start-index (- start-index pivot-size)
		      adj-end-index (- end-index pivot-size)]
			(if (>= adj-start-index 0)
				(let [pivot-seq (subs l start-index end-index)
				      adjoining-seq (subs l adj-start-index adj-end-index)]
					(if (= pivot-seq adjoining-seq)
						true
						(recur l (dec start-index) end-index)))
				false))))

(defn easy?
	"Detects, if the specified seq contains occurrence of two adjoining identical subsequences.
	Tries to check for every index and goes from the end to the start."
	[l]
	(let [l (upper-case l)]
		(loop [start-index (dec (count l))]
			(cond
				(< start-index 0) false
				(easy-at-index? l start-index) true
				:else (recur (dec start-index))))))

(defn hard?
	"A handly wrapper around negaive easy? fn."
	[l]
	(not (easy? l)))

(defn promote-seq-down
	"Extend sequence down using the provided first letter. If seq is [A B] and first letter is B, then the result
	will be [A B B]."
	[s letters]
	(conj s (first letters)))

(defn promote-seq-next
	"Promotes the last character in the provided sequence to the next one using the provided letters. If the last
	character is the last one in the provided letters list, then pop the last value and change the previous value.
	For example, if sequence is [A C] and letters are [A B C], then the result is B."
	[s letters]
	(let [last-letter (last s)
	      next-letter (fnext (drop-while #(not (= % last-letter)) letters))]
		(cond
			(nil? last-letter) []
			next-letter (conj (pop s) next-letter)
			:else (recur (pop s) letters))))

(defn permutated-lazy-seq
	"A lazy sequence of permutated letters increasing alphabetical order, forming a 'hard' sequence. If 2 arg version
	is used, then start-seq must not be empty."
	([letters]
		(permutated-lazy-seq (promote-seq-down [] letters) letters))
	([start-seq letters]
		(let [step (fn [current-seq letters]
		                ;; TODO: replace easy/hard to work with seq instead of strings?
						(let [current-string (reduce str "" current-seq)]
							(cond
								(empty? current-string) nil
								(hard? current-string)
									(cons current-string
									      (permutated-lazy-seq (promote-seq-down current-seq letters) letters))
								:else (recur (promote-seq-next current-seq letters) letters))))]
			(lazy-seq (step start-seq letters)))))

(defn hard-seq
	"Gets the Nth hard sequence with letters in range of L"
	[n L]
	(let [letters (map char (range 65 (+ 65 L)))] ;; ascii codes of capital letters
	      (nth (permutated-lazy-seq letters) (dec n))))


;; TODO: read-line
;; TODO: wrapper around take and generator
;; TODO: printout the seq and it's size
