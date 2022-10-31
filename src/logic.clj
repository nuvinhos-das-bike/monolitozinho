(ns logic
  (:require [model.database :as m.database]
            [model.point :as m.point]
            [schema.core :as s]))



(defn retirada-bike [id-bike id-usuario db]
  ;; retira point da bike
  ;; adiciona o user a bike
  )

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
