(ns nu-challenge.core
  (:gen-class)
  (:require [clojure.data.json :as json]
		  			[nu-challenge.input :refer :all]
		  			[nu-challenge.formatting :refer :all]
		  			[nu-challenge.computations :refer :all]))

(defn solve-me
  "I reveive an input for the Reward System challenge and return the solution."
  [main-arg]
  (def input (read-input :file main-arg))
  (println "Input is:" input)
  (def solution (compute-scores input))
  (build-ranking-map solution))

(defn formatted-solution
	"I receive and input for the Reward System challenge and return the results in the chosen format."
	[chosen-format main-arg]
	(apply-format chosen-format (solve-me main-arg)))
	
(defn -main
  "I orchestrate the whole thing. I'm the leader here!"
  [main-arg]
  (println (formatted-solution :json main-arg))
  0)
