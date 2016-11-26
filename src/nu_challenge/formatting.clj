(ns nu-challenge.formatting
  (:gen-class)
  (:require [clojure.data.json :as json]))

; ----------------------------------------------------------------------------------------------------
; begin of formatting functions
(defn to-json
	"I receive a map of any depth (the values of the map may be maps) and return it in the json format."
	[output]
	(json/write-str output))

(defn apply-format
  "I put the output in the desired format."
  [show-format output]
  (if (= show-format :raw)
  		(str "Score: " output)
  (if (= show-format :json)
  		(to-json output))))
; end of formatting functions
; ----------------------------------------------------------------------------------------------------