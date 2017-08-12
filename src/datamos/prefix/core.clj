(ns datamos.prefix.core
  "Prefix URI mapping for dataMos platform

  base URI model in dataMos:
  http:// {domain} / {category} / {type} / {concept} + {check} + {reference} (note the '+'(plus-sign) between concept, check and reference) .

  domain    = the domain name for the specific URI. Eg: datamos.org
  category  = the category the data referenced by the URI belongs to. Currently this is one of:
                data - basically all URI's belong in this category except for the ones below:
                config - URI's which define configuration of datamos or components.
                function - URI's which refer to a function as supplied by a component
  type      = is the type of resouce the URI references to. Could be one of the following values:
                id - for data instances
                def - for data definitions
  concept   = The kind of concept the URI is refering to
  check     = Abbreviated prefix, to make a visual check between prefix and entity possible.
                Supply when {type} = id. Otherwise leave it out.
  reference = The unique id, to refer to. Supply when {type} = id. Otherwise leave it out.

  Prefixes refer to URI's up until the last slash ('/') So prefixes only refer to:
  http:// {domain} / {category} / {type} /"
  (:require [datamos
             [core :as dc]
             [communication :as dcom]
             [base :as base]
             [sign-up :as sup]
             [module-helpers :as hlp]]
            [mount.core :as mnt :refer [defstate]]
            [taoensso.timbre :as log]
            [taoensso.timbre.appenders.core :as appenders]))

; TODO - Create the main registry for prefixes in dataMos
; TODO - Create helper function in dataMos to retrieve keyword namespaces from messages
; TODO - Send message with prefix keywords to prefix module
; TODO - Retrieve message contents with prefix keywords
; TODO - Create helper functions in dataMos to add prefixes to messages
; TODO - Register prefix module and functions

(def remote-components (atom {}))

(def known-prefixes
  {:datamos "http://ld.datamos.org/data/id/"
   :dms-def "http://ld.datamos.org/data/def/"
   :dms-rdf "http://ld.datamos.org/rdf/"
   :datamos-cfg "http://ld.datamos.org/config/id/"
   :dmscfg-def "http://ld.datamos.org/config/def/"
   :datamos-fn "http://ld.datamos.org/function/id/"
   :dmsfn-def "http://ld.datamos.org/function/def/"
   :rdfs "http://www.w3.org/2000/01/rdf-schema#"
   :rdf "http://www.w3.org/1999/02/22-rdf-syntax-ns#"})

(def ^:private prefixes (atom {}))

(defn get-prefixes
  [rdf-map]
  (select-keys @prefixes
               (map keyword
                    (set (keep namespace
                               (filter keyword?
                                       (tree-seq coll? seq
                                                 rdf-map)))))))
(defn local-register
  []
  @remote-components)

(defn match-prefix
  [_ _ message]
  (println message))

(def component-fns (merge {:datamos/match-prefix datamos.prefix.core/match-prefix}
                          (hlp/local-module-register remote-components)))

(base/component-function {:datamos-cfg/module-type :datamos-fn/core
                          :datamos-cfg/module-fn   :datamos-fn/prefix
                          :datamos-cfg/local-register (datamos.prefix.core/local-register)
                          :dms-def/provides           datamos.prefix.core/component-fns})

(defn -main
  "Initializes datamos.core. Configures the exchange"
  [& args]
  (swap! prefixes assoc known-prefixes))
