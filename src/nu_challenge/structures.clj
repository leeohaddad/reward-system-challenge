(ns nu-challenge.structures
  (:gen-class))

; ----------------------------------------------------------------------------------------------------
; begin of data structure handling functions
;(defn build-hash-map-entry
;	"I receive an index and a value and return a map entry (pair-vector) using them."
;	[target-index target-value]
;	[(+ target-index 1) target-value])
;
;(defn vector-to-hash-map
;	"I receive a vector and return the map with same values with indexes as keys."
;	[input-vector]
;	(reduce conj {} (map-indexed build-hash-map-entry input-vector)))
;
(defn get-customer
	"I receive a vector of parameters and extract the inviter customer id from it."
	[root-node]
	(first root-node))

(defn get-invitees
	"I receive a vector of parameters and extract the invitees id from it."
	[root-node]
	(rest root-node))
; end of data structure handling functions
; ----------------------------------------------------------------------------------------------------