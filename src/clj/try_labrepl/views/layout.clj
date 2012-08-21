(ns try-labrepl.views.layout
  (:use [noir.core :only [defpartial]]
        [hiccup.core :only (html)]
        [hiccup.element :only (link-to)]
        [hiccup.page :only (html5 include-css include-js)]))

(defn- include-clojurescript [path]
  (list
   [:script {:type "text/javascript"}
    "var CLOSURE_NO_DEPS = true;"]
   (include-js path)))

(def default-stylesheets
  ["/stylesheets/shCore.css"
   "/stylesheets/shThemeDefault.css"
   "/stylesheets/application.css"])

(def default-javascripts
  ["http://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"
   "/javascripts/jquery-console/jquery.console.js"
   "/javascripts/shCore.js"
   "/javascripts/shBrushClojure.js"])

(defn include-javascripts
  []
  (concat (apply include-js default-javascripts) (include-clojurescript "/javascripts/app.js")))

(defn home [body]
  (html5
    [:head
     [:title "Clojure Labs"]
     (apply include-css default-stylesheets)]
    [:body [:div {:id "header"} [:h2.logo "Clojure Labs"]]
     [:div {:id "content"} body]
     [:div {:id "footer"} "Clojure labrepl. Copyright Relevance Inc. All Rights Reserved."]
     (include-javascripts)]))

(defn navigation [link-data]
  [:div {:id "breadcrumb"}
   [:div {:id "previous"} (if-let [prev (:prev link-data)]
                            (link-to (:prev-url link-data) (str "Previous Lab: " prev))
                            (link-to "/" "Home"))]
   [:div {:id "next"} (if-let [next (:next link-data)]
                        (link-to (:next-url link-data) (str "Next Lab: " next))
                        (link-to "/" "Home"))]])

(defn clj-console []
  [:div#console-wrapper
   [:div#console.console]])

(defn lab [title link-data & body]
  {:pre [(string? (last title))]}
  (html5
    [:head
     [:title (last title)]
     (apply include-css default-stylesheets)]
    [:body [:div {:id "header"} title]
     [:div {:id "content"}
      (navigation link-data)
      [:div {:class "clearfix"}]
      [:div {:id "inner-content"}
       [:div {:class "sidebar-toggle"}
        [:img {:src "/images/terminal.png"}]
        [:a {:href "javascript:void(null)"} "Toggle REPL"]]
       body]
      [:div {:id "sidebar"} (clj-console)]]
     [:div {:id "footer"} "Clojure labrepl. Copyright Relevance Inc. All Rights Reserved."]
     (include-javascripts)]))

(defn not-found []
  (let [title "Page Not Found"]
    (html5
     [:head
      [:title title]]
     [:body
      [:h1 title]])))