(ns db.user)

(defn get-user-by-key [api-key db]
  (->> @db
       :users
       (filter #(= (:key (val %)) api-key))
       (take 1)
       (map #(assoc (val %) :id (key %)))
       first))