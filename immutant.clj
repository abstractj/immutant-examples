;; Ring support

(ns beer.init
  (:use beer.core)
  (:require [immutant.messaging :as msg]
            [immutant.web :as web]))

(defn beer-handler [request]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body "Immutant up and running!"})

(web/start "/" beer-handler)

;; Messaging
(def myqueue "/queue/myqueue")
(msg/start myqueue)
(msg/listen myqueue #(println "received: " %))
(msg/publish myqueue "This is polyglot love")

;; Messaging allows for starting (and stopping) destinations (queues & topics)
;; and listening for messages on a destination.

; (messaging/start "/queue/a-queue")
; (messaging/listen "/queue/a-queue" #(println "received: " %))

