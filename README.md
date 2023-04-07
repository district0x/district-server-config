# district-server-config

[![CircleCI](https://dl.circleci.com/status-badge/img/gh/district0x/district-server-config/tree/master.svg?style=svg)](https://dl.circleci.com/status-badge/redirect/gh/district0x/district-server-config/tree/master)

Clojurescript-node.js [mount](https://github.com/tolitius/mount) module for a district server, that takes care of loading configuration from a file at system start.

## Installation
Add `[district0x/district-server-config "1.5.0-SNAPSHOT"]` into your project.clj
Include `[district.server.config]` in your CLJS file, where you use `mount/start`.

**Warning:** district0x modules are still in early stages, therefore API can change in a future.

## Real-world example
To see how district server modules play together in real-world app, you can take a look at [NameBazaar server folder](https://github.com/district0x/name-bazaar/tree/master/src/name_bazaar/server),
where this is deployed in production.

## Usage
You can pass following args to config module:
* `:default` Default configuration that will be deep-merged with the one read from a file
* `:env-name` Name of ENV variable that stores path to config file. Default: `CONFIG`
* `:file-path` Path to a config file. Default: `config.edn`, but won't throw error if doesn't exist

Config file is expected to be in [EDN](https://github.com/edn-format/edn) format.
```clojure
(ns my-district
  (:require [mount.core :as mount]
            [district.server.config :refer [config]]))

(-> (mount/with-args
      {:config {:default {:something 1}
                :env-name "DISTRICT_CONFIG"
                ;; Use one of :env-name or :file-path
                :file-path "config.edn"}})
  (mount/start))

(println @config)
;; => {:something 1 :some-other-thing 2}
```

## Development, test & release

1. Build: `npx shadow-cljs compile test-node`
  - also need to deploy contracts: `npx truffle migrate --network ganache --reset`
2. Tests: `node out/node-tests.js`

To release (happens automatically on CI at merge to master)

1. Build: `clj -T:build jar`
2. Release: `clj -T:build deploy`
