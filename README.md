# district-server-config

Clojurescript-node.js [mount](https://github.com/tolitius/mount) component for a district server, that takes care of loading configuration from a file at system start. 

## Installation
Add `[district0x/district-server-config "1.0.0"]` into your project.clj  
Include `[district.server.config]` in your CLJS file, where you use `mount/start`.

**Warning:** district components are still in early stages, therefore API can change in a future.

## Usage
You can pass following args to config component:   
* `:default` Default configuration that will be deep-merged with the one read from file  
* `:env-name` Name of ENV variable that stored path to config file  
* `:file-path` Path to a config file  

Config file is expected to be in JSON format.
```clojure
(ns my-district
  (:require [mount.core :as mount]
            [district.server.config :refer [config]]))

(-> (mount/with-args
      {:config {:default {:something 1}
                :env-name "DISTRICT_CONFIG"
                ;; Use one of :env-name or :file-path
                :file-path "config.json"}})
  (mount/start))

(println @config)
;; => {:something 1 :some-other-thing 2}
```