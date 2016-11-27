(ns nu-challenge.structures
  (:gen-class)
  (:require [clojure.set :as set]))

; ----------------------------------------------------------------------------------------------------
(defn get-root
  "I find out which inviters has not been invited."
  [current-input]
  (set/difference (get current-input :inviters) (get current-input :invitees)))

(defn set-root
  "I set the input [:root] information."
  [current-input]
  (update-in current-input [:root] (fn [x] (get-root current-input))))
      
(defn customer-has-invited
  "I check if a customer has already invited someone."
  [current-input customer]
  (if (contains? (get current-input :inviters) customer)
          true
          false))
      
(defn customer-was-invited
  "I check if a customer was already invited."
  [current-input customer]
  (if (contains? (get current-input :invitees) customer)
          true
          false))

(defn register-inviter
  "I update the input [:inviters] information."
  [current-input invitee]
  (update-in current-input [:inviters] (fn [x] (conj (get current-input :inviters) invitee))))

(defn register-invitee
  "I update the input [:invitees] information."
  [current-input invitee]
  (if (customer-has-invited current-input invitee)
      current-input
      (update-in current-input [:invitees] (fn [x] (conj (get current-input :invitees) invitee)))))

(defn apply-changes
  "I update the input informations outside :data."
  [current-input invitation]
  (register-invitee (register-inviter current-input (first invitation)) (last invitation)))

(defn add-first-time-invitation
  "I add a new first-time invitation to the current input."
  [current-input invitation]
  (if (contains? (get current-input :data) (first invitation))
      (update-in current-input [:data (first invitation) 0] (fn [x] (conj (get-in current-input [:data (first invitation) 0]) (last invitation))))
      (update-in current-input [:data (first invitation)]  (fn [x] [#{(last invitation)} #{}]))))
      
(defn add-redundant-invitation
  "I add a new redundant invitation to the current input."
  [current-input invitation]
  (if (contains? (get current-input :data) (first invitation))
      (update-in current-input [:data (first invitation) 1] (fn [x] (conj (get-in current-input [:data (first invitation) 1]) (last invitation))))
      (update-in current-input [:data (first invitation)] (fn [x] [#{} #{(last invitation)}]))))

(defn is-first-time-invitation
  "I check if some new invitation is first-time."
  [current-input invitation]
  (if (customer-has-invited current-input (last invitation))
      false
      (if (customer-was-invited current-input (last invitation))
          false
          true)))

(defn add-invitation
  "I add a new invitation to the current input."
  [current-input invitation]
  (if (< (count invitation) 2)
      current-input
      (apply-changes (if (is-first-time-invitation current-input invitation)
        (add-first-time-invitation current-input invitation)
        (add-redundant-invitation current-input invitation)) invitation)))

(defn get-first-time-invitees
	"I tell you who are the first-time invitees of the customer."
	[invitations-data customer]
  (def my-first-time-invitees (first (get invitations-data customer)))
	(if (= my-first-time-invitees nil)
      #{}
      my-first-time-invitees))

(defn get-redundant-invitees
	"I tell you who are the redundant invitees of the customer."
	[invitations-data customer]
	(def my-redundant-invitees (second (get invitations-data customer)))
  (if (= my-redundant-invitees nil)
      #{}
      my-redundant-invitees))
; end of data structure handling functions
; ----------------------------------------------------------------------------------------------------