(defproject district0x/district-server-config "1.0.1"
  :description "district0x server module for loading configuration"
  :url "https://github.com/district0x/district-server-config"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [
                 ; [cljs-node-io "0.5.0"]
                 [com.github.pkpkpk/cljs-node-io "2.0.332"]
                 [mount "0.1.11"]
                 [javax.xml.bind/jaxb-api "2.3.0"]
                 [org.clojure/clojurescript "1.9.946"]]

  :npm {:devDependencies [[ws "2.0.1"]]}

  :figwheel {:server-port 4672}

  :profiles {:dev {:dependencies [[org.clojure/clojure "1.8.0"]
                                  [com.cemerick/piggieback "0.2.2"]
                                  [figwheel-sidecar "0.5.14"]
                                  [org.clojure/tools.nrepl "0.2.13"]]
                   :plugins [[lein-cljsbuild "1.1.7"]
                             [lein-figwheel "0.5.14"]
                             [lein-npm "0.6.2"]
                             [lein-doo "0.1.7"]]
                   :source-paths ["dev"]}}

  :cljsbuild {:builds [{:id "tests"
                        :source-paths ["src" "test"]
                        :figwheel {:on-jsload "tests.runner/-main"}
                        :compiler {:main "tests.runner"
                                   :output-to "tests-compiled/run-tests.js"
                                   :output-dir "tests-compiled"
                                   :target :nodejs
                                   :optimizations :none
                                   :source-map true}}]})
