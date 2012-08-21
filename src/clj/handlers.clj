(ns handlers
  (:use [clojure.tools.logging :only (info)]
        [ring.middleware.head :only [wrap-head]]
        [ring.util.response :only [status]]))

(defn with-logging [handler]
  (fn [request]
    (let [start (System/nanoTime)
          response (handler request)
          elapsed (/ (double (- (System/nanoTime) start)) 1000000.0)]
      (when response
        (info (str (.toUpperCase (name (:request-method request))) " " (:uri request) " " elapsed " milliseconds"))
        response))))
