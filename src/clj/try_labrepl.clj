(ns ^{:author "Stu Halloway"
      :doc "Compojure app that displays lab instructions."}
  try-labrepl
  (:use [clojure.tools.logging :only (info)]
        [compojure.core :only (defroutes GET)]
        [ring.adapter.jetty :only (run-jetty)]
        [try-labrepl.util :only (make-url)])
  (:require [noir.server :as server]
            [noir.statuses :as status]
            [compojure.route :as route]
            [handlers :as handlers]
            [try-labrepl.views.layout :as layout]))

(server/load-views "src/clj/try_labrepl/views/")
(server/add-middleware handlers/with-logging)

;; (status/set-page! 404 (layout/not-found))

(defn -main [& m]
  (let [mode (keyword (or (first m) :dev))
        port (Integer. (get (System/getenv) "PORT" "8080"))]
    (server/start port {:mode mode
                        :ns 'try-labrepl})))
