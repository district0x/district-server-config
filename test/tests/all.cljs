(ns tests.all
  (:require
    [cljs.test :refer-macros [deftest is testing use-fixtures]]
    [district.server.config :refer [config]]
    [mount.core :as mount]))

(use-fixtures
  :each
  {:after
   (fn []
     (mount/stop))})

(deftest test-default-config
  (-> (mount/with-args {:config {:default {:a 1}}})
    (mount/start))
  (is (= {:a 1} @config)))

(deftest test-default-config-and-from-file
  (-> (mount/with-args {:config {:default {:a 1}
                                 :file-path "resources/config.edn"}})
    (mount/start))
  (is (= {:a 1 :b 1} @config)))

(deftest test-default-config-and-from-env
  (aset js/process.env "MY_CONFIG" "resources/config.edn")
  (-> (mount/with-args {:config {:default {:c "hello"}
                                 :env-name "MY_CONFIG"}})
    (mount/start))
  (is (= {:c "hello" :b 1} @config)))
