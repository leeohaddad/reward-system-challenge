(ns nu-challenge.core
  (:gen-class))

; ----------------------------------------------------------------------------------------------------
; begin of input functions
(defn get-hardcoded-input
  "I store and delivery hardcoded inputs. Call example: '(get-hardcoded-input :example)'."
  [input-id]
  (if (= input-id :example)
			[1 [2] [3 [4 [5] [6]]]]))

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

(defn solve-me
  "I reveive an input for the Reward System challenge and print the results in the requested format."
  [& input]
  (do (println "Didn't solve yet!")
  		(println "Input is:" input)
  		(show-output :std :raw "solution")))

(defn -main
  "I orchestrate the whole thing quickly and efficiently. I'm the leader here!"
  [main-arg]
  (solve-me (read-input :file main-arg)))
