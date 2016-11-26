(ns nu-challenge.structures
  (:gen-class))

; ----------------------------------------------------------------------------------------------------
(defn update-root
  "I update the input [:root] information. I assume no cycles (A->B->C->A)."
  [current-input invitation]
  (if (= (get current-input :root) -1)
      (update-in current-input [:root] (fn [x] (first invitation)))
      (if (= (get current-input :root) (last invitation))
          (update-in current-input [:root] (fn [x] (first invitation)))
          current-input)))

(defn register-invitee
  "I update the input [:invitees] information."
  [current-input invitee]
  (update-in current-input [:invitees] (fn [x] (conj (get current-input :invitees) invitee))))

(defn register-inviter
  "I also update the input [:invitees] information."
  [current-input inviter]
  (update-in current-input [:invitees] (fn [x] (conj (get current-input :invitees) inviter))))

(defn apply-changes
  "I update the input informations outside :data."
  [current-input invitation]
  (update-root (register-invitee (register-inviter current-input (first invitation)) (last invitation)) invitation))

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
  (if (contains? (get current-input :invitees) (last invitation))
      false
      true))

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