(ns modulo-3.db
  (:require [com.stuartsierra.component :as component]
            [schema.core :as s]))

(defrecord Database [config]
  component/Lifecycle

  (start [this]
    (let [_ (println config)
          arquivo (-> (-> config :config :db-file)
                      slurp
                      read-string)]
      (assoc this :db (atom arquivo))))

  (stop [this]
    (println "Limpar o db.edn da memória e Salvar em disco para uso futuro.")
    (assoc this :store nil)))

(defn new-database []
  (->Database {}))

(s/defn find-point-by-id
  [db :- Database
   id :- s/Int]
  (get-in db [:points (keyword (str id))]))
