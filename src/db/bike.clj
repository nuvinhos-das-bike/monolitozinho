(ns db.bike
  (:require [model.bike :as m.bike]
            [schema.core :as s]))

(s/defn get-all-bikes [db] :- m.bike/Bikes
  (get @db :bikes))

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
