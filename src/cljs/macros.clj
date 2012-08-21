(ns macros)

(defmacro ready
  [& body]
  `(util/document-ready (fn []
                          ~@body)))