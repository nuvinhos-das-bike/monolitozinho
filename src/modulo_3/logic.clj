(ns modulo-3.logic
  (:require [modulo-3.models :as m]
            [schema.core :as s]
            [modulo-3.db :as db]))

(s/defn ^:always-validate all-points [db :- m/Database] :- m/Points
  (get db :points))

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

(defn get-point
  "Get one specific point by id"
  [{:keys [db point-id]}]
  (let [point (db/find-point-by-id db point-id)]
    (if (not (nil? point))
      {:status 200 :body point}
      {:status 404})))
