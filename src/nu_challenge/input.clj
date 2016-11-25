(ns nu-challenge.input
  (:gen-class)
  (:require [nu-challenge.structures :refer :all]))

; ----------------------------------------------------------------------------------------------------
; begin of input functions
(defn get-hardcoded-input
  "I store and delivery hardcoded inputs. Call example: '(get-hardcoded-input :example)'."
  [input-id]
  (if (= input-id :example)
    {:root 1 :data {1 [#{2 3} #{}] 2 [#{} #{4}] 3 [#{4} #{}] 4 [#{5 6} #{}] 5 [#{} #{}] 6 [#{} #{}]} :invitees #{}}))

(defn simulate-invitations-input
  "I simulate the sequential usage of function add-invitation to buid an input!"
  [current-input]
  (add-invitation (add-invitation (add-invitation (add-invitation (add-invitation (add-invitation current-input [1 2]) [1 3]) [3 4]) [2 4]) [4 5]) [4 6]))

(defn get-invitation-from-file
  "I get the next invitation of the input file."
  ; But for now I just fake it.
  [source-file fake-arg]
  [source-file fake-arg])

(defn add-invitations-from-file
  "I add invitations from some specified file to the current input."
  ; But for now I just fake it.
  [current-input source-file]
  (simulate-invitations-input current-input))
  ;(add-invitation current-input (get-invitation-from-file source-file)))

(defn read-input-from-file
  "I go get the input from some specified file."
  [source-file]
  (add-invitations-from-file {:root -1 :data {} :invitees #{}} source-file))

(defn read-input
  "I go after the input using the mode chosen by the caller!"
  [read-mode read-arg]
  (if (= read-mode :file)
  		(read-input-from-file read-arg)
  (if (= read-mode :hardcoded)
      (get-hardcoded-input read-arg))))
; end of input functions
; ----------------------------------------------------------------------------------------------------