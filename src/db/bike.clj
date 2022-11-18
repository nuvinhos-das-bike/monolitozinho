(ns db.bike
  (:require [model.bike :as m.bike]
            [schema.core :as s]
            [datomic.client.api :as d]))

(s/defn get-all-bikes [conn] :- m.bike/Bikes
  (d/q '[:find (pull ?e [*])
         :where [?e :bike/id]] (d/db conn)))

(s/defn get-bike [id-bike db]
  (get-in @db [:bikes id-bike]))

(s/defn request-bike [id-bike id-user db]
  (swap! db (fn [db] (update-in db [:bikes id-bike] #(-> %
                                                         (assoc :user id-user)
                                                         (dissoc :point))))))
(defn return-bike [id-bike id-ponto db]
  (swap! db (fn [db] (update-in db [:bikes id-bike] #(-> %
                                                         (assoc :point id-ponto)
                                                         (dissoc :user))))))
