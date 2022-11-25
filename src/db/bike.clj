(ns db.bike
  (:require [model.bike :as m.bike]
            [schema.core :as s]
            [datomic.client.api :as d]))

(s/defn get-all-bikes [conn] :- m.bike/Bikes
  (d/q '[:find (pull ?e [*])
         :where [?e :bike/id]] (d/db conn)))

(defn get-from-user [user conn]
  (-> (d/pull (d/db conn) '[{:user/bike [*]}] user) :user/bike))

(s/defn get-bike [id-bike conn]
  (d/q '[:find ?bike
         :in $ ?id-bike
         :where [?bike :bike/id ?id-bike]] (d/db conn) id-bike))

(s/defn request-bike [id-bike id-user db]
  (swap! db (fn [db] (update-in db [:bikes id-bike] #(-> %
                                                         (assoc :user id-user)
                                                         (dissoc :point))))))
(defn return-bike! [id-bike id-ponto conn]
  (d/transact conn {:tx-data [{:point/id    id-ponto
                               :point/bikes [{:bike/id id-bike}]}]}))
