(ns try-labrepl
  (:use [util :only [clj->js press-enter]]))

(def clj-console (atom nil))
(def clj-console-enabled (atom false))
(def $ js/jQuery)

(defn enable-syntax-highlighting
  []
  (. js/SyntaxHighlighter (all)))

(defn toggle-code-blocks
  []
  (let [sel ($ ".toggle a")]
    (.click sel (fn [e]
                  (this-as this
                           (let [sel ($ this)
                                 parents (.parents sel ".toggle")
                                 children (.children parents)]
                             (.toggle children)))))))

(defn expand-width
  []
  (let [sels [($ "#header") ($ "#content") ($ "#footer")]]
    (doseq [sel sels]
      (. sel addClass "expanded-width"))))

(defn contract-width
  []
  (let [sels [($ "#header") ($ "#content") ($ "#footer")]]
    (doseq [sel sels]
      (. sel removeClass "expanded-width"))))

(defn expand-inner-content
  []
  (. ($ "#content #inner-content") css "width" "740px"))

(defn contract-inner-content
  []
  (. ($ "#content #inner-content") css "width" "600px"))

(defn expand-sidebar-content
  []
  (let [sb ($ "#content #sidebar")
        csole (. sb find "#console")]
    (. csole addClass "console-visible")
    (. sb css "width" "410px")))

(defn contract-sidebar-content
  []
  (let [sb ($ "#content #sidebar")
        csole (. sb find "#console")]
    (. csole removeClass "console-visible")
    (. sb css "width" "0px")))

(defn reset-console
  []
  (. @clj-console reset))

(defn enable-console-reset
  []
  (let [sel ($ "#console-reset")]
    (.click sel (fn [e] (reset-console)))))

(defn add-console-reset
  []
  (let [csole-wrapper ($ "#sidebar #console-wrapper")
        ;; if we do the following much more, we'll pull in crate
        reset-ctrl (str "<div id='console-reset'>"
                        "<a href='javascript:void(null)'>Reset REPL</a>"
                        "</div>")]
    (. csole-wrapper append reset-ctrl)
    (enable-console-reset)))

(defn remove-console-reset
  []
  (let [reset-sel ($ "#console-wrapper #console-reset")]
    (. reset-sel remove)))

(defn hide-sidebar
  "Hide the sidebar in which the console is displayed"
  []
  (let [header ($ "#header")
        content ($ "#content")
        footer ($ "#footer")
        sb (. content find "#sidebar")
        csole (. sb find "#console")
        reset-repl (. sb find "#console-reset")
        inner-content (. content find "#inner-content")]
    ;; Remove tooling
    (remove-console-reset)
    ;; Hide console, now that it's instantiated, should be fine
    (. csole hide)
    ;; Shrink sidebar content
    (contract-sidebar-content)
    ;; Grow inner-content to original
    (expand-inner-content)
    ;; Shrink overall width to original
    (contract-width)))

(defn eval-clojure
  [code]
  (let [data (atom nil)
        eval-args (clj->js {"url" "/eval.json"
                            "data" {"expr" code}
                            "async" false
                            "success" (fn [res]
                                        (reset! data res))
                            "error" (fn [res]
                                      (. js/console log res))})]
    (. $ ajax eval-args)
    @data))

(defn on-validate
  [input]
  (not= input ""))

(defn on-handle
  [line report]
  (let [input (. $ trim line)
        data (eval-clojure input)]
    (if (.-error data)
      (clj->js [{"msg" (.-message data)
                 "className" "jquery-console-message-error"}])
      (clj->js [{"msg" (.-result data)
               "className" "jquery-console-message-value"}]))))

(defn enable-clj-console
  []
  (let [sel ($ "#console")
        console-args (clj->js {"welcomeMessage:" "Enter Clojure code to be evaluated."
                               "promptLabel" "Clojure> "
                               "commandValidate" on-validate
                               "commandHandle" on-handle
                               "autofocus" true
                               "animateScroll" true
                               "promptHistory" true})]
    (reset! clj-console (. sel console console-args))))

(defn show-sidebar
  "Show the sidebar in which the console is displayed"
  []
  (let [content ($ "#content")
        header ($ "#header")
        footer ($ "#footer")
        sb (. content find "#sidebar")
        csole (. sb find "#console")
        csole-wrapper (. sb find "#console-wrapper")
        inner-content (. content find "#inner-content")]
    (expand-width)
    (contract-inner-content)
    (expand-sidebar-content)
    ;; Append tooling
    (add-console-reset)
    (if-not @clj-console-enabled
      (do
        (reset! clj-console-enabled true)
        ;; Already auto-focuses the console
        (enable-clj-console))
      (do
        (. csole show)
        (. csole click)))))

(defn toggle-sidebar
  []
  (let [sel ($ ".sidebar-toggle")]
    (.click sel (fn [e]
                  (this-as this
                           (let [sel ($ "#content #sidebar #console")]
                             (if (. sel hasClass "console-visible")
                               (hide-sidebar)
                               (show-sidebar))))))))

;; Support simply clicking on code snippets and having them pasted
;; into the console

(defn enable-code-pasting
  []
  (let [sel ($ ".syntaxhighlighter.clojure")
        csole ($ "#content #sidebar #console")
        click-fn (fn [e]
                   (this-as this
                            (let [sel ($ this)
                                  txt (. sel text)]
                              (. @clj-console promptText txt)
                              (. csole click)
                              (press-enter csole)
                              (press-enter csole))))
        each-fn (fn []
                  (this-as this
                           (let [sel ($ this)]
                             (. sel css "cursor" "pointer")
                             (. sel attr "title", (str "Click to insert '"
                                                       (. sel text)
                                                       "' into the console."))
                             (. sel click click-fn))))]
    (. sel each each-fn)))
