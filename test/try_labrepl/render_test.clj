(ns try-labrepl.render-test
  (:use clojure.test
        try-labrepl try-labrepl.views.core try-labrepl.util)
  (:require [noir.server :as server]))

(server/load-views "src/noir-example/views")

(deftest render-the-labs
  []
  (doseq [lab all]
    (let [url (lab-url lab)
          application (server/gen-handler {:mode :dev
                                       :ns 'try-labrepl})
          resp (application {:request-method :get
                             :uri url})]
      (is
       (= {:status 200
           :headers  
           {"Content-Type" "text/html; charset=utf-8" "Set-Cookie" ()}}
          (select-keys resp 
                       [:status :headers]))))))
