(ns nu-challenge.core
  (:gen-class))

(def dp [0])

; ----------------------------------------------------------------------------------------------------
; begin of input functions
(defn get-hardcoded-input
  "I store and delivery hardcoded inputs. Call example: '(get-hardcoded-input :example)'."
  [input-id]
  (if (= input-id :example)
			[1 [2 [4]] [3 [4 [5] [6]]]]))

(defn read-input-from-file
  "I go get the input from some specified file."
  [source-file]
  (do (println "My job is to get the input using a file. But, for now, I only fake the input.")
  		(get-hardcoded-input :example)))

(defn read-input
  "I go after the input using the mode chosen by the caller!"
  [read-mode read-arg]
  (if (= read-mode :file)
  		(read-input-from-file read-arg)))
; end of input functions
; ----------------------------------------------------------------------------------------------------

; ----------------------------------------------------------------------------------------------------
; begin of output functions
(defn to-json
	"I convert the output to the json format."
	[output]
	(output ".json"))

(defn apply-format
  "I put the output in the desired format."
  [show-format output]
  (if (= show-format :raw)
  		output
  (if (= show-format :json)
  		(to-json output))))

(defn show-output-httpep
  "I show the output using a http endpoint."
  [output]
  (println output ".httpep"))

(defn show-output
  "I show the output using the mode chosen by the caller!"
  [destination show-format output]
  (def formatted-output (apply-format show-format output))
  (if (= destination :std)
  		(println formatted-output)
  (if (= destination :httpep)
  		(show-output-httpep formatted-output))))
; end of output functions
; ----------------------------------------------------------------------------------------------------

(defn get-consumer
	"I receive a vector of parameters and extract the inviter consumer id from it."
	[root-node]
	(first root-node))

(defn get-invitees
	"I receive a vector of parameters and extract the invitees id from it."
	[root-node]
	(rest root-node))

(defn complete-vector
	"I make sure that the vector is big enough for some index."
	[input-vector target-index]
	(def result (conj input-vector 0))
	(if (= (count result) target-index)
			result
			(complete-vector result target-index)))

(defn compute-score
	"I receive a sub-tree of inviter-invitee relations and compute the score for the root customer."
	[root-node]
	(def total-score (reduce + (map compute-score (get-invitees root-node))))
	(def consumer (- (get-consumer root-node) 1))
	(if (< (count dp) (+ consumer 1))
			(def dp (complete-vector dp (+ consumer 1))))
	(def dp (assoc dp consumer (+ (get dp consumer) total-score)))
	(println "Finished calculations for #" consumer "=" (get dp consumer))
	(def direct-contribution (if (= (count root-node) 1)
																0
																1))
	(def indirect-contribution (/ total-score 2.0))
	(println "  Direct contribution to inviter:" direct-contribution "\n  Indirect contribution to inviter:" indirect-contribution)
	(+ direct-contribution indirect-contribution))

(defn solve-me
  "I reveive an input for the Reward System challenge and print the results in the requested format."
  [& input]
  (println "Didn't solve yet!")
  (println "Input is:" input)
  (compute-score (first input))
  (println "Scores:" dp)
  (show-output :std :raw "solution"))

(defn -main
  "I orchestrate the whole thing quickly and efficiently. I'm the leader here!"
  [main-arg]
  (solve-me (read-input :file main-arg)))
