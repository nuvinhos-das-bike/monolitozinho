(ns adapter.user
  (:require [clojure.set]))

(defn datomic->user
  [user]
  (clojure.set/rename-keys user {:user/id         :id
                                 :user/key        :key
                                 :user/subscriber :subscriber
                                 :user/login      :login
                                 :user/bike       :bike}))

(defn datomic->users [users]
  (mapv datomic->user users))
