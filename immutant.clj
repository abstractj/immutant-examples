;; Ring support

;(ns beer.init
  ;(:use beer.core)
;  (:require [immutant.messaging :as messaging]
;            [immutant.web :as web]))

;(defn beer-handler [request]
;  {:status 200
;   :headers {"Content-Type" "text/html"}
;   :body "Immutant up and running!"})

;(web/start "/" beer-handler)

;; Noir support
(ns beer.init
  (:use beer.core)
  (:require [immutant.web :as web]
            [noir.server :as server]
            [beer.core :as core]))

(server/load-views (str (web/src-dir) "/beer/views"))
(web/start "/" (server/gen-handler {:mode :dev :ns 'beer}))

;; This file will be loaded when the application is deployed to Immutant, and
;; can be used to start services your app needs. Examples:


;; Web endpoints need a context-path and ring handler function. The context
;; path given here is a sub-path to the global context-path for the app
;; if any.

; (web/start "/" my-ring-handler)
; (web/start "/foo" a-different-ring-handler)

;; To start a Noir app:
; (server/load-views (str (web/src-dir) "/beer/views"))
; (web/start "/" (server/gen-handler {:mode :dev :ns 'beer}))


;; Messaging allows for starting (and stopping) destinations (queues & topics)
;; and listening for messages on a destination.

; (messaging/start "/queue/a-queue")
; (messaging/listen "/queue/a-queue" #(println "received: " %))

