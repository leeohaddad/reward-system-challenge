(ns nu-challenge.handler
  (:require [nu-challenge.core :refer :all]
  					[compojure.core :refer :all]
            [compojure.route :as route]
            [ring.util.response :as resp]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

(defroutes app-routes
  (GET "/" [] (resp/redirect "/index.html"))
  (GET "/getRanking" [] (formatted-solution :json "input.txt"))
  (route/not-found "Not Found"))

(def app
  (wrap-defaults app-routes site-defaults))
