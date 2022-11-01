(ns wire.out.point
  (:require [schema.core :as s]
            [common-core.schema :as schema]
            [wire.out.address :as w.o.address]
            [wire.out.bike :as w.o.bike]))

(def canonical-point
  {:id       s/Keyword
   :name     s/Str
   :capacity s/Num
   :address  w.o.address/Address
   :bikes    w.o.bike/Bikes})

(s/defschema Point
  (schema/strict-schema canonical-point))

(s/defschema Points
  (schema/strict-schema
    [Point]))