;; Ring support

(ns beer.init
  (:use beer.core)
  (:require [immutant.messaging :as msg]
            [immutant.web :as web]
            [immutant.daemons :as daemon]
            [immutant.jobs :as jobs]))

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

(defn start []
  (reset! done false)
  (loop [i 0]
    (Thread/sleep 1000)
    (when-not @done
      (listener)
      (recur (inc i)))))

(defn stop []
  (reset! done true))

;; Register the daemon
(daemon/daemonize "beerdaemon" start stop :singleton false)

;; Scheduling


(jobs/schedule "beer-job" "*/2 * * * * ?"
  #(println "Job was called!")
  :singleton false)


