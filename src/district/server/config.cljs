(ns district.server.config
  (:require
    [cljs-node-io.core :refer [slurp]]
    [cljs-node-io.fs :refer [file?]]
    [cljs.reader :as reader]
    [mount.core :as mount :refer [defstate]]))

(declare load)
(defstate config :start (load (:config (mount/args))))

(def env js/process.env)

(letfn [(merge-in* [a b]
          (if (map? a)
            (merge-with merge-in* a b)
            b))]
  (defn merge-in
    "Merge multiple nested maps."
    [& args]
    (reduce merge-in* nil args)))

(defn load
  ([]
   (load {}))
  ([{:keys [:default :env-name :file-path]
     :or {env-name "CONFIG"}}]
   (let [path (or file-path (aget env env-name))
         path (if (and (empty? path)
                       (file? "config.edn"))
                "config.edn"
                path)
         file-config (when path
                       (reader/read-string (slurp path)))]
     (merge-in default file-config))))