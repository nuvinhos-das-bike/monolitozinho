(ns db.bike
  (:require [model.bike :as m]
            [schema.core :as s]
            [datomic.client.api :as d]))

(s/defn get-bike :- m/Bike
  [id-bike :- s/Uuid
   conn]
  (-> (d/q '[:find (pull ?e [[:bike/id :as :id]])
             :in $ ?id
             :where [?e :bike/id ?id]] (d/db conn) id-bike)
      ffirst))

(s/defn request-bike
  [id-bike :- s/Uuid
   id-user :- s/Uuid
   conn]

  (let [point-id (ffirst (d/q '[:find  ?point
                                :in    $ ?bike-id
                                :where [?bike :bike/id ?bike-id]
                                       [?point :point/bikes ?bike]] (d/db conn) id-bike))]

    (d/transact conn {:tx-data [[:db/retract point-id :point/bikes [:bike/id id-bike]]
                                [:db/add [:user/id id-user] :user/bike [:bike/id id-bike]]]})))

(defn return-bike! [id-bike id-point id-user conn]
  (d/transact conn {:tx-data [{:point/id    id-point
                               :point/bikes [{:bike/id id-bike}]}
                              [:db/retract [:user/id id-user] :user/bike [:bike/id id-bike]]]}))
