(ns util)

(def $ js/jQuery)

(defn  clj->js
  "Recursively transforms ClojureScript maps into Javascript objects,
   other ClojureScript colls into JavaScript arrays, and ClojureScript
   keywords into JavaScript strings.

   Borrowed and updated from mmcgrana."
  [x]
  (cond
    (string? x) x
    (keyword? x) (name x)
    (map? x) (.-strobj (reduce (fn [m [k v]]
               (assoc m (clj->js k) (clj->js v))) {} x))
    (coll? x) (apply array (map clj->js x))
    :else x))

(defn document-ready [func]
  (.ready ($ js/document) func))

(defn press-enter [jq-sel]
  (let [e (. $ Event "keypress")]
    (aset e "which" 13)
    (aset e "keyCode" 13)
    (. jq-sel trigger e)))