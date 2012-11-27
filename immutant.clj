;; Ring support

(ns beer.init
  (:use beer.core)
  (:require [immutant.messaging :as msg]
            [immutant.web :as web]
            [immutant.daemons :as daemon]))

(defn beer-handler [request]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body "Immutant up and running!"})

(web/start "/" beer-handler)

;; Messaging
(def myqueue "/queue/myqueue")
(msg/start myqueue)
(msg/listen myqueue #(println "received: " %))

(defn listener
  "Starting the daemon"
  []
  (msg/publish myqueue "This is polyglot love"))

;; Daemon
(def done (atom false))

(defrecord BeerService []
  daemon/Daemon
  (start [_]
    (reset! done false)
    (loop [i 0]
      (Thread/sleep 1000)
      (when-not @done
        (listener)
        (recur (inc i)))))
  (stop [_] (reset! done true)))

;; Register the daemon
(daemon/create "beerdaemon" (BeerService.) :singleton true)


;; Messaging allows for starting (and stopping) destinations (queues & topics)
;; and listening for messages on a destination.

; (messaging/start "/queue/a-queue")
; (messaging/listen "/queue/a-queue" #(println "received: " %))

