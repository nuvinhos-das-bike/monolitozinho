(ns modulo-3.config
  (:require [com.stuartsierra.component :as component]))

(defrecord Config []
  component/Lifecycle
  (start [this]
    (let [arquivo (-> "resources/config.edn"
                      slurp
                      read-string)]
      (assoc this :config arquivo)))

  (stop [this]
    (dissoc this :config))
  )

(defn new-config []
  (->Config))