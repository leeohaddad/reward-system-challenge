(ns nu-challenge.core
  (:gen-class))

(def scores-dp [0])

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

; ----------------------------------------------------------------------------------------------------
; begin of data structure handling functions
(defn get-customer
	"I receive a vector of parameters and extract the inviter customer id from it."
	[root-node]
	(- (first root-node) 1))

(defn get-invitees
	"I receive a vector of parameters and extract the invitees id from it."
	[root-node]
	(rest root-node))

(defn fill-vector
	"I make sure that the vector is big enough for some index."
	[input-vector target-index]
	(def result (conj input-vector 0.0))
	(if (= (count result) target-index)
			result
			(fill-vector result target-index)))

(defn ensure-bounds
	"I check if the vector is big enough for some index"
	[target-vector target-index]
	(if (< (count target-vector) target-index)
			(fill-vector target-vector target-index)
			target-vector))
; end of data structure handling functions
; ----------------------------------------------------------------------------------------------------

; ----------------------------------------------------------------------------------------------------
; begin of computations functions
(defn my-indirect-contribution 
	"I am the one responsible for computing how much a customer contributes indirectly to it's inviter's score."
	[total-score]
	(/ total-score 2.0))

(defn my-direct-contribution
	"I am the one responsible for computing how much a customer contributes directly to it's inviter's score."
	[customer-subtree]
	(if (= (count customer-subtree) 1)
			0
			1))

(defn compute-my-contribution
	"I am the one responsible for computing how much a customer contributes to it's inviter's score."
	[customer-subtree total-score]
	(+ (my-direct-contribution customer-subtree) (my-indirect-contribution total-score)))

(defn compute-score
	"I receive a sub-tree of inviter-invitee relations and compute the score for the root customer.
	 I store the customer score in the scores-dp and I return the custumer contribution to it's inviter's score."
	[customer-subtree]
	(def scores-dp (ensure-bounds scores-dp (+ (get-customer customer-subtree) 1)))
	(def invitees-contribution (reduce + (map compute-score (get-invitees customer-subtree))))
	(def total-score (+ invitees-contribution (get scores-dp (get-customer customer-subtree))))
	(def scores-dp (assoc scores-dp (get-customer customer-subtree) total-score))
	(println "Finished calculations for #" (get-customer customer-subtree) "=" (get scores-dp (get-customer customer-subtree)))
	(compute-my-contribution customer-subtree total-score))
; end of computations functions
; ----------------------------------------------------------------------------------------------------

(defn solve-me
  "I reveive an input for the Reward System challenge and print the results in the requested format."
  [& input]
  (println "Didn't solve yet!")
  (println "Input is:" input)
  (compute-score (first input))
  (show-output :std :raw "solution")
  (println "Scores:" scores-dp))

(defn -main
  "I orchestrate the whole thing quickly and efficiently. I'm the leader here!"
  [main-arg]
  (solve-me (read-input :file main-arg)))
