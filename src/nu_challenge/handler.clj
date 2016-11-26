(ns nu-challenge.handler
  (:require [nu-challenge.solution :refer :all]
  					[nu-challenge.structures :refer :all]
  					[compojure.core :refer :all]
            [compojure.route :as route]
            [ring.util.response :as resp]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

(defroutes app-routes
  (GET "/" [] (load-input-and-redirect "/index.html"))
  (GET "/getRanking" [] (formatted-solution :json my-data))
  (GET "/addInvitation" [from to] (formatted-solution :json (add-invitation my-data [from to])))
  (route/not-found "Not Found"))

(def app
  (wrap-defaults app-routes site-defaults))