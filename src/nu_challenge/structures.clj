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

(defn apply-changes
  "I update the input informations outside :data."
  [current-input invitation]
  (update-root (register-invitee current-input (last invitation)) invitation))

(defn add-valid-invitation
  "I add a new valid invitation to the current input."
  [current-input invitation]
  (if (contains? (get current-input :data) (first invitation))
      (update-in current-input [:data (first invitation) 0] (fn [x] (conj (get-in current-input [:data (first invitation) 0]) (last invitation))))
      (update-in current-input [:data (first invitation)]  (fn [x] [#{(last invitation)} #{}]))))
      
(defn add-invalid-invitation
  "I add a new invalid invitation to the current input."
  [current-input invitation]
  (if (contains? (get current-input :data) (first invitation))
      (update-in current-input [:data (first invitation) 1] (fn [x] (conj (get-in current-input [:data (first invitation) 1]) (last invitation))))
      (update-in current-input [:data (first invitation)] (fn [x] [#{} #{(last invitation)}]))))
      
(defn is-valid-invitation
  "I check if some new invitation is valid."
  [current-input invitation]
  (if (contains? (get current-input :invitees) (last invitation))
      false
      true))

(defn add-invitation
  "I add a new invitation to the current input."
  [current-input invitation]
  (apply-changes (if (is-valid-invitation current-input invitation)
      (add-valid-invitation current-input invitation)
      (add-invalid-invitation current-input invitation)) invitation))

(defn get-valid-invitees
	"I tell you who are the valid invitees of the customer."
	[invitations-data customer]
	(first (get invitations-data customer)))

(defn get-invalid-invitees
	"I tell you who are the invalid invitees of the customer."
	[invitations-data customer]
	(second (get invitations-data customer)))
; end of data structure handling functions
; ----------------------------------------------------------------------------------------------------