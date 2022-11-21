(ns model.point
  (:require [schema.core :as s]
            [model.address :as m.address]
            [model.bike]))

(def skeleton-point
  {:id       s/Uuid
   :name     s/Str
   :capacity s/Num
   :address  m.address/Address
   :bikes    [model.bike/Bike]})

(s/defschema Point skeleton-point)

(s/defschema Points [Point])
