(ns modulo-3.logic
  (:require [modulo-3.models :as m]
            [schema.core :as s]))

(s/defn ^:always-validate all-points [db :- m/Database] :- m/Points
  (get db :points))

(defn has-it-capacity? [db id-ponto]
  (let [vals-of-bikes (vals (get db :bikes))
        all-bikes-in-point (filter #(= id-ponto (:point %)) vals-of-bikes)
        capacity (-> db
                     :points
                     (get id-ponto)
                     :capacity)]
    (< (count all-bikes-in-point) capacity)))

(defn bike-devolution [id-bike id-ponto db]
  (if (has-it-capacity? db id-ponto)
    (update-in db [:bikes id-bike] #(-> %
                                        (assoc :point id-ponto)
                                        (dissoc :user)))
    (throw (ex-info "point-full" {}))))

(defn bike-request [id-bike id-user db]
  (update-in db [:bikes id-bike] #(-> %
                                      (assoc :user id-user)
                                      (dissoc :point))))

(defn get-user-by-key [api-key db]
  (->> db
       :users
       (filter #(= (:key (val %)) api-key))
       (take 1)
       (map #(assoc (val %) :id (key %)))
       first))
