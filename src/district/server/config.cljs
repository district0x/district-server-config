(ns district.server.config
  (:require
    [cljs-node-io.core :refer [slurp]]
    [cljs-node-io.fs :refer [file?]]
    [clojure.walk :as walk]
    [cognitect.transit :as transit]
    [mount.core :as mount :refer [defstate]]))

(declare load)
(defstate config :start (load (:config (mount/args))))

(def env js/process.env)
(def reader (transit/reader :json))

(letfn [(merge-in* [a b]
          (if (map? a)
            (merge-with merge-in* a b)
            b))]
  (defn merge-in
    "Merge multiple nested maps."
    [& args]
    (reduce merge-in* nil args)))

(defn load
  "Load the config overriding the defaults with values from process.ENV (if exist)."
  ([]
   (load {}))
  ([{:keys [:default :env-name :file-path]
     :or {env-name "CONFIG"}}]
   (let [path (or file-path (aget env env-name))
         path (if (and (empty? path)
                       (file? "config.json"))
                "config.json"
                path)
         env-config (when path
                      (-> (transit/read reader (slurp path))
                        walk/keywordize-keys))]
     (merge-in default env-config))))
