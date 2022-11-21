(ns db.point
  (:require [model.point :as model.point]
            [schema.core :as s]
            [datomic.client.api :as d]))

(def point-pattern
  '[[:point/id :as :id]
    [:point/name :as :name]
    [:point/capacity :as :capacity]
    {[:point/address :as :address] [[:address/id :as :id]
                                    [:address/street :as :street]
                                    [:address/number :as :number]
                                    [:address/zip-code :as :zip-code]
                                    [:address/address-line :as :address-line]]}
    {[:point/bikes :as :bikes] [[:bike/id :as :id]]}])

(s/defn get-all-points :- model.point/Points
  [conn]
  (->> (mapv first (d/q '[:find (pull ?e pattern)
                          :in $ pattern
                          :where [?e :point/id]] (d/db conn) point-pattern))
       ))

(s/defn get-point :- model.point/Point
  [id :- s/Uuid
   conn]
  (ffirst (d/q '[:find (pull ?e pattern)
                 :in $ pattern ?id
                 :where [?e :point/id ?id]] (d/db conn) point-pattern id)))
