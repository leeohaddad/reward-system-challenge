(ns nu-challenge.computations
  (:gen-class)
  (:require [nu-challenge.structures :refer :all]))

; ----------------------------------------------------------------------------------------------------
; begin of computations functions
(defn sum-of-invitees-scores
	"I'm the one who gives the sum of the scores from all the invitees of a customer."
	[customer-subtree current-scores]
	(reduce + (map (fn [invitee-subtree] (if (= (get current-scores (first invitee-subtree)) nil)
																						0.0
																						(get current-scores (first invitee-subtree)))) (rest customer-subtree))))

(defn indirect-score
	"I'm the one who calculates the indirect score that a customer will earn with his invitees confirmed invitations."
	[customer-subtree current-scores]
	(println "customer-subtree" customer-subtree)
	(if (> (count (rest customer-subtree)) 0)
			(/ (sum-of-invitees-scores customer-subtree current-scores) 2.0)
			0.0))

(defn check-confirm
	"I'm the one who checks how many confirmed invitations a customer has made."
	[invitee]
	(if (> (count (rest invitee)) 0)
			1.0
			0.0))

(defn direct-score
	"I'm the one who calculates the direct score that a customer will earn through his confirmed invitations."
	[customer-subtree]
	(if (> (count (rest customer-subtree)) 0)
			(reduce + (map check-confirm (rest customer-subtree)))
			0.0))

(defn compute-score
	"I compute the score for some specified costumer."
	[customer-subtree current-scores]
	(+ (direct-score customer-subtree) (indirect-score customer-subtree current-scores)))

(defn compute-scores
	"I compute the scores of all the costumers and return it as a hash-map."
	[customer-subtree]
	(def result (if (> (count (get-invitees customer-subtree)) 0)
			(apply merge (map compute-scores (get-invitees customer-subtree)))
			{}))
	(apply merge result {(get-customer customer-subtree) (compute-score customer-subtree result)}))

(defn build-ranking-map
	"I receive a hash-map of scores and return it as a map structure."
	[scores-hash-map]
	(hash-map "ranking" (reduce conj {} (sort-by last > scores-hash-map))))
; end of computations functions
; ----------------------------------------------------------------------------------------------------