(defproject nu-challenge "0.1.0-SNAPSHOT"
  :description "Solution for the Reward System challenge."
  :url "https://github.com/leeohaddad/reward-system-challenge"
  :min-lein-version "2.0.0"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
  							 [compojure "1.5.1"]
                 [ring/ring-defaults "0.2.1"]
                 [ring/ring-core "1.3.2"]
  							 [org.clojure/data.json "0.2.6"]]
  :main ^:skip-aot nu-challenge.solution
  :target-path "target/%s"
  :plugins [[lein-ring "0.9.7"]]
  :ring {:handler nu-challenge.handler/app}
  :profiles {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring/ring-mock "0.3.0"]]}, :uberjar {:aot :all}})
