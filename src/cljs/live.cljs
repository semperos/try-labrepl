;; Where live code comes to live
(ns live
  (:require [try-labrepl :as l]
            [util :as u])
  (:require-macros [macros :as m]))

(def $ js/jQuery)

(m/ready
 (l/enable-syntax-highlighting)
 (l/toggle-code-blocks)
 (l/toggle-sidebar)
 ;; Give SyntaxHighlighter a chance to change the DOM first
 (js/setTimeout l/enable-code-pasting 1000))
