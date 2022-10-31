(defproject modulo-3 "0.1.0-SNAPSHOT"
  :description "Shared bikes from nuvinhos das bikes"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [org.clojure/test.check "1.1.1"]
                 [io.pedestal/pedestal.service "0.5.10"]
                 [io.pedestal/pedestal.route "0.5.10"]
                 [io.pedestal/pedestal.jetty "0.5.10"]
                 [org.slf4j/slf4j-simple "2.0.3"]
                 [clj-http "3.12.3"]
                 [com.stuartsierra/component "1.1.0"]
                 [walmartlabs/system-viz "0.4.0"]
                 [common-core        "16.14.1"]
                 [hiccup "1.0.5"]]
  :repl-options {:init-ns modulo-3.core})
