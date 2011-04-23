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
        (let [l (vec l) ;; in case string is passed
              pivot-size (- end-index start-index)
              adj-start-index (- start-index pivot-size)
              adj-end-index (- end-index pivot-size)]
            (if (>= adj-start-index 0)
                (let [pivot-seq (subvec l start-index end-index)
                      adjoining-seq (subvec l adj-start-index adj-end-index)]
                    (if (= pivot-seq adjoining-seq)
                        true
                        (recur l (dec start-index) end-index)))
                false))))

(defn hard-at-last-index?
    "checks if the sequence hard when using last index as a pivot point"
    [l]
    (not (easy-at-index? l (dec (count l)))))

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
            (cond
                (empty? current-seq) nil
                (hard-at-last-index? current-seq)
                (cons current-seq
                    (permutated-lazy-seq (promote-seq-down current-seq letters) letters))
                :else (recur (promote-seq-next current-seq letters) letters)))]
            (lazy-seq (step start-seq letters)))))

(defn hard-seq
    "Gets the Nth hard sequence with letters in range of L"
    [n L]
    (let [letters (map char (range 65 (+ 65 L)))] ;; ascii codes of capital letters
        (when (> n 0)
            (nth (permutated-lazy-seq letters) (dec n) nil))))

(defn seq-pretty-print
    "Pretty prints the specified sequence. Split it into groups of four (4) characters separated by a space.
     If there are more than 16 such groups, start a new line for the 17th group"
    [s]
    (let [grouped-chars (partition-all 4 s)
          grouped-strings (map #(reduce str "" %) grouped-chars)
          grouped-new-lined-strings (interpose "\n" (partition-all 16 grouped-strings))]
        (reduce str "" (flatten (map #(interpose " " %) grouped-new-lined-strings)))))



(defn krypton-factor
    "Prints out the hard sequence according to the provided input and on the next line prints the length of that
      sequence.
      Example invocations:
      (krypton-factor 1000 26)
      (krypton-factor 7 3)
      (krypton-factor 0 1)
      "
    [n L]
    (let [hard-one (hard-seq n L)]
        (println (seq-pretty-print hard-one))
        (println (count hard-one))))

