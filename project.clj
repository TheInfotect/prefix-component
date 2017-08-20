(defproject org.volenta/datamos.prefix "0.1.6.0"
  :description "Prefix module for dataMos."
  :url "http://theinfotect.org/datamos"
  :license {:name "GNU AFFERO GENERAL PUBLIC LICENSE, Version 3"
            :url "https://www.gnu.org/licenses/agpl-3.0.nl.html"}
  :dependencies [[org.clojure/clojure "1.9.0-alpha17"]
                 [org.clojure/tools.namespace "0.2.11"]
                 [org.volenta/datamos "0.1.6.0"]
                 [com.taoensso/timbre "4.10.0"]
                 [mount "0.1.11"]]
  :main ^:skip-aot datamos.prefix.core
  :test-paths ["test"]
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}
             :dev     {:dependencies [[org.clojure/test.check "0.10.0-alpha2"]]}}
  :repositories [["releases" {:url "https://clojars.org/repo"
                              :creds :gpg}]])
