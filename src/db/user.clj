(ns db.user
     (:require [datomic.client.api :as d]))

(defn get-user-by-key [api-key conn]
  (ffirst (d/q '[:find ?user
                 :in $ ?api-key
                 :where [?user :user/key ?api-key]] (d/db conn) api-key)))