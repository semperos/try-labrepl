(defproject try-labrepl "0.0.2-SNAPSHOT"
  :description "Clojure exercises, with integrated repl and webapp"
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [org.clojure/tools.logging "0.2.4"]
                 [org.clojure/data.json "0.1.3"]
                 [clojail "0.6.1"]
                 [noir "1.3.0-beta10" :exclusions [org.clojure/clojure
                                                   hiccup]]
                 [hiccup "1.0.1" :exclusions [org.clojure/clojure]]
                 [log4j "1.2.17" :exclusions [javax.mail/mail
                                              javax.jms/jms
                                              com.sun.jdmk/jmxtools
                                              com.sun.jmx/jmxri]]
                 [jline "2.7"]]
  :source-path "src/clj"
  :jvm-opts ["-Djava.security.policy=labrepl.policy""-Xmx80M"]
  :main try-labrepl
  :plugins [[lein-cljsbuild "0.2.5"]]
  :cljsbuild {:builds [{:source-path "src/cljs"
                        :compiler {:output-to "resources/public/javascripts/app.js"
                                   :optimizations :simple
                                   :pretty-print true}}]})
