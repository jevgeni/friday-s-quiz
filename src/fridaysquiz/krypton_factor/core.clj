(ns fridaysquiz.krypton_factor.core)

(defn easy?
	"Detects, if the specified seq contains occurrence of two adjoining identical subsequences"
	[l]
	true)

(defn easy-at-index?
	"Checks the occurence of two adjoing identical subsequences. The first subsequence starts at start-index (inclusive)
	and ends at end-index (exclusive).
	The second sequence is adjoining the first sequence on the left side. If the subsequences do not match, then the check is
	continued for first subsequence of different size: the first sequence's start-index is decreased by one, second sequence is
	recalculated and compared. The recursion ends when there are not possible to calculate adjoined sequence anymore."
	[l start-index end-index]

	(let [pivot-seq (subs l start-index end-index)
	      pivot-size (- end-index start-index)
	      adj-start-index (- start-index pivot-size)
	      adj-end-index (- end-index pivot-size)]
		(if (>= adj-start-index 0)
			(let [adjoining-seq (subs l adj-start-index adj-end-index)]
				(if (= pivot-seq adjoining-seq)
					true
					(recur l (dec start-index) end-index)))
			false)))

