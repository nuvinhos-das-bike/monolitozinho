(ns wire.out.point
  (:require [schema.core :as s]
            [wire.out.address :as w.o.address]
            [wire.out.bike :as w.o.bike]))

(def canonical-point
  {:id       s/Keyword
   :name     s/Str
   :capacity s/Num
   :address  w.o.address/Address
   :bikes    w.o.bike/Bikes})

(s/defschema Point canonical-point)

(s/defschema Points [Point])