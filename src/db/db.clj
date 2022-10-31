(ns db.db
  (:require [com.stuartsierra.component :as component]))

(defrecord Database [config]
  component/Lifecycle

  (start [this]
    (let [_ (println config)
          arquivo (-> (-> config :config :db-file )
                      slurp
                      read-string)]
      (assoc this :db (atom arquivo))))

  (stop [this]
    (println "Limpar o db.edn da memória e Salvar em disco para uso futuro.")
    (assoc this :store nil)))

(defn new-database []
  (->Database {}))