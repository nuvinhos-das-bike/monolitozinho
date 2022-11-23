(ns db.db
  (:require [com.stuartsierra.component :as component]
            [datomic.client.api :as d]
            [db.schema]
            [db.seed]))

(defn delete-database []
  (let [config (-> "resources/config.edn" slurp read-string)
        client (d/client (select-keys config [:system :server-type]))]
    (d/delete-database client (select-keys config [:db-name]))))

(defn prepare-database []
  (let [config (-> "resources/config.edn" slurp read-string)
        client (d/client (select-keys config [:system :server-type]))
        _ (d/create-database client (select-keys config [:db-name]))
        conn (d/connect client (select-keys config [:db-name]))
        _ (d/transact conn {:tx-data (concat db.schema/address-schema
                                             db.schema/point-schema
                                             db.schema/bike-schema
                                             db.schema/user-schema)})]

        (d/transact conn {:tx-data [db.seed/rua-da-felicidade
                                    db.seed/ponto-da-felicidade
                                    db.seed/ponto-da-euforia
                                    db.seed/user-admin
                                    db.seed/user-user
                                    db.seed/user-anon]})))


(defrecord Database [config]
  component/Lifecycle

  (start [this]
    (let [config (-> config :config)
          client (d/client (select-keys config [:system :server-type]))
          _ (d/create-database client (select-keys config [:db-name]))
          conn (d/connect client (select-keys config [:db-name]))]
      (assoc this :conn conn)))

  (stop [this]
    (println "Limpar o db.edn da memória e Salvar em disco para uso futuro.")
    (assoc this :store nil)))

(defn new-database []
  (->Database {}))

(comment
  (prepare-database)
  (delete-database)

  (def config (-> "resources/config.edn" slurp read-string))
  (def client (d/client (select-keys config [:system :server-type])))
  (d/create-database client (select-keys config [:db-name]))
  (def conn (d/connect client (select-keys config [:db-name])))

  (d/q '[:find (pull ?e [*])
         :where [?e :user/id]] (d/db conn))

  (d/q '[:find (pull ?e [*])
         :where [?e :address/id]] (d/db conn))

  (d/q '[:find (pull ?e [*])
         :where [?e :bike/id]] (d/db conn))

  (ffirst (d/q '[:find ?e
                 :in $ ?bike
                 :where [?e :point/bikes ?bikes]] (d/db conn) (java.util.UUID/fromString "34f85161-9c1e-4150-8833-71f895ea6507"))))