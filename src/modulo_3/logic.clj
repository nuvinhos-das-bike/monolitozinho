(ns modulo-3.logic)

(def db-provisorio (atom
                     {:points {:1 {:name     "ponto da felicidade"
                                   :capacity 10
                                   :address  {:street       "rua da felicidade"
                                              :number       "12a"
                                              :zip-code     "72000000"
                                              :address-line ""}}
                               :2 {:name     "ponto da euforia"
                                   :capacity 50
                                   :address  {:street       "rua da felicidade"
                                              :number       "12a"
                                              :zip-code     "72000000"
                                              :address-line ""}}}
                      :bikes  {:1 {:point 1
                                   :user  nil}
                               :2 {:point 1
                                   :user  nil}
                               :3 {:point 2
                                   :user  nil}
                               :4 {:point nil
                                   :user  1}}
                      :users  {:1 {:login      "admin"
                                   :subscribed true
                                   :key        "/&[3-.wff@qx'{aTX-2P>}XE_B6Jc+"}}}))

(defn retirada-bike [request]
  (let [id-bike (-> request :path-params :id-bike keyword)
        id-user (-> request :path-params :id keyword)
        bike (-> @db-provisorio :bikes id-bike)]
    ;(println bike)

    (if (= id-user (:user bike))
      (throw (Exception. "Bike já vinculada ao usuário.")))

    (if (:user bike)
      (throw (Exception. "Bike vinculada a outro usuário.")))

    ;; retira point da bike
    (swap!
      db-provisorio
      assoc-in [:bikes id-bike :point] nil)

    ;; adiciona o user a bike
    (swap!
      db-provisorio
      assoc-in [:bikes id-bike :user] id-user)

    ;(println (-> @db-provisorio
    ;             :bikes
    ;             id-bike))
    ;(println @db-provisorio)
    {:status 200 :body {:id-bike id-bike
                        :id-user id-user
                        :id-point (-> @db-provisorio
                                      :bikes
                                      id-bike
                                      :point)}}))

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

