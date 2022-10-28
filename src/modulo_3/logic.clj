(ns modulo-3.logic)

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
  (let [db-dissoc (update-in db [:bikes id-bike] dissoc :user)]
    (if (has-it-capacity? db id-ponto)
      (update-in db-dissoc [:bikes id-bike] assoc :point id-ponto)
      (throw (ex-info "point-full" {})))))

