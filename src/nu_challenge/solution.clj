(ns nu-challenge.solution
  (:gen-class)
  (:require [clojure.data.json :as json]
            [ring.util.response :as resp]
		  			[nu-challenge.input :refer :all]
		  			[nu-challenge.formatting :refer :all]
		  			[nu-challenge.computations :refer :all]))

(def debug-on false)
(def my-data {:root -1 :data {} :invitees #{}})

(defn solve-me
  "I reveive an input for the Reward System challenge and return the solution."
  [input]
  (def my-data input)
  (if debug-on
      (println "\n >> The input is:" input "\n"))
  (def solution (compute-scores input))
  (build-ranking-map solution))

(defn formatted-solution
	"I receive and input for the Reward System challenge and return the results in the chosen format."
	[chosen-format input]
	(apply-format chosen-format (solve-me input)))

(defn read-input-and-do-the-job
  "I read the input from main-arg file and return the output of the problem solution."
  [chosen-format main-arg]
  (formatted-solution chosen-format (read-input :file main-arg)))

(defn load-input-and-redirect
  "I read the input from resources/input.txt and return the output of the problem solution."
  [redirect-to]
  (def my-data (read-input :file "resources/example.txt"))
  (resp/redirect redirect-to))

(defn -main
  "I orchestrate the whole thing. I'm the leader here!"
  [main-arg]
  (if debug-on
      (println " >> The result is:" (read-input-and-do-the-job :json main-arg))
      (println (read-input-and-do-the-job :json main-arg)))
  0)
