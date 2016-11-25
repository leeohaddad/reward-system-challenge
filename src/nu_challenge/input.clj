(ns nu-challenge.input
  (:gen-class))

; ----------------------------------------------------------------------------------------------------
; begin of input functions
(defn get-hardcoded-input
  "I store and delivery hardcoded inputs. Call example: '(get-hardcoded-input :example)'."
  [input-id]
  (if (= input-id :example)
    {:root 1 :data {1 [#{2 3} #{}] 2 [#{} #{4}] 3 [#{4} #{}] 4 [#{5 6} #{}] 5 [#{} #{}] 6 [#{} #{}]}}))
			;[1 [2 [4]] [3 [4 [5] [6]]]]))

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