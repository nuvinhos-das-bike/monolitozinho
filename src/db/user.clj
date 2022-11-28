(ns db.user
     (:require [schema.core :as s]
               [datomic.client.api :as d]
               [adapter.user]
               [model.user]))

(s/defn get-user-by-key :- model.user/User
     [api-key :- s/Str
      conn]
     (-> (d/q '[:find (pull ?e [:user/id
                                :user/key
                                :user/login
                                :user/subscriber
                                {:user/bike [[:bike/id :as :id]]}])
                :in $ ?key
                :where [?e :user/key ?key]] (d/db conn) api-key)
         ffirst
         adapter.user/datomic->user))

(s/defn get-user :- model.user/User
  [id-user :- s/Uuid
   conn]
  (-> (d/q '[:find (pull ?e [:user/id
                             :user/key
                             :user/login
                             :user/subscriber
                             {:user/bike [[:bike/id :as :id]]}])
             :in $ ?id
             :where [?e :user/id ?id]] (d/db conn) id-user)
      ffirst
      adapter.user/datomic->user))

(s/defn get-users :- model.user/Users
  [conn]
  (->> (d/q '[:find (pull ?e [:user/id
                             :user/key
                             :user/login
                             :user/subscriber
                              {:user/bike [[:bike/id :as :id]]}])
             :where [?e :user/id]] (d/db conn))
      (mapv first)
      adapter.user/datomic->users))