(ns nu-challenge.computations
  (:gen-class)
  (:require [nu-challenge.structures :refer :all]))

; ----------------------------------------------------------------------------------------------------
; begin of computations functions
(defn sum-of-invitees-scores
	"I'm the one who gives the sum of the scores from all the invitees of a customer."
	[invitations-data customer current-scores first-time-invitees]
	(reduce + (map (fn [invitee] (if (= (get current-scores invitee) nil)
																						0.0
																						(get current-scores invitee))) first-time-invitees)))

(defn indirect-score
	"I'm the one who calculates the indirect score that a customer will earn with his invitees confirmed invitations."
	[invitations-data customer current-scores first-time-invitees]
	(if (> (count first-time-invitees) 0)
			(/ (sum-of-invitees-scores invitations-data customer current-scores first-time-invitees) 2.0)
			0.0))

(defn check-confirm
	"I'm the one who checks if an invitations is valid."
	[invitations-data invitee]
	(if (> (+ (count (get-first-time-invitees invitations-data invitee)) (count (get-redundant-invitees invitations-data invitee))) 0)
			1.0
			0.0))

(defn direct-score
	"I'm the one who calculates the direct score that a customer will earn through his confirmed invitations."
	[invitations-data customer first-time-invitees]
	(if (> (count first-time-invitees) 0)
			(reduce + (map (fn [invitee] (check-confirm invitations-data invitee)) first-time-invitees))
			0.0))

(defn compute-final-score
	"I compute the final score for some specified customer."
	[invitations-data customer current-scores first-time-invitees]
	(+ (direct-score invitations-data customer first-time-invitees) (indirect-score invitations-data customer current-scores first-time-invitees)))

(defn compute-score
	"I compute the score for some specified customer. I'll meditate about this def and its side effects later (TODO)."
	[invitations-data customer]
	(def compute-score-result (if (> (count (get-first-time-invitees invitations-data customer)) 0)
			(apply merge (map (fn [next-customer] (compute-score invitations-data next-customer)) (get-first-time-invitees invitations-data customer)))
			{}))
	(apply merge compute-score-result {customer (compute-final-score invitations-data customer compute-score-result (get-first-time-invitees invitations-data customer))}))

(defn compute-scores
	"I compute the scores of all the customers and return it as a hash-map."
	[invitations]
	(if (= (get invitations :root) -1)
			{}
			(compute-score (get invitations :data) (get invitations :root))))

(defn build-ranking-map
	"I receive a hash-map of scores and return it as a map structure."
	[scores-hash-map]
	(hash-map "ranking" (reduce conj {} (sort-by last > scores-hash-map))))
; end of computations functions
; ----------------------------------------------------------------------------------------------------