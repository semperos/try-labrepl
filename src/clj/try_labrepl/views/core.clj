(ns try-labrepl.views.core
  (:require [try-labrepl.views.layout :as l]
            [try-labrepl.util :as u]
            [clojure.tools.logging :as log])
  (:use [noir.core :only [defpage]]))

(def all [:intro
          :names-and-places
          :its-all-data
          :looping
          :project-euler
          :mini-browser
          :unified-update-model
          :zero-sum
          :cellular-automata
          :defstrict
          :rock-paper-scissors])

(defn home []
  (l/home
   [:ul
    (map
     (fn [lab] [:li (u/make-url lab)])
     all)]))

(defn instructions
  [lab]
  ((ns-resolve lab 'instructions)))

(defn render-lab [lab]
  (let [lab-ns (symbol (str "labs." lab))]
    (require lab-ns)
    (l/lab [:h2 lab]
                (meta (find-ns lab-ns))
                (instructions lab-ns))))


(defpage "/" []
  (log/info "Please log me!!")
  (home))

(defpage "/labs/:name" {:keys [name]}
  (render-lab name))



;; (defroutes lab-routes
;;   (GET "/" [] (home))
;;   (GET "/labs/:name" [name] (render-lab name))
;;   (route/files "/")
;;   (route/not-found "<h1>Not Found</h1>"))