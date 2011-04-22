(ns fridaysquiz.krypton_factor.core)

(defn easy?
	"Detects, if the specified seq contains occurrence of two adjoining identical subsequences"
	[l]
	true)

(defn easy-at-index?
	"Checks the occurence of two adjoing identical subsequences. The first subsequence starts at start-index (inclusive)
	and ends at end-index (exclusive).
	The second sequence is adjoining the first sequence on the left side. If the subsequences do not match, then the check is
	continued for first subsequence of different size. The first sequence's start-index is decreased by one, second sequence is
	recalculated and compared. The recursion ends when the start-index becomes smaller that the end-index, as, obviously, there
	are no more any adjoining identical subsequences exist"
	[l start-index end-index]

	(let [pivot-seq (subs l start-index end-index)
	      size (- end-index start-index)
	      adj-start-index (- start-index size)
	      adj-end-index (- end-index size)
	      adjoining-seq (subs l adj-start-index adj-end-index)]
		(= pivot-seq adjoining-seq)))

