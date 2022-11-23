(ns controller.user
  (:require [db.user]
            [schema.core :as s]))

(s/defn get-user
        [user-id :- s/Uuid
         db-conn]
        (db.user/get-user user-id db-conn))

(s/defn get-users
  [db-conn]
  (db.user/get-users db-conn))