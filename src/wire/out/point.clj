(ns wire.out.point
  (:require [schema.core :as s]
            [common-core.schema :as schema]
            [wire.out.address :as w.o.address]))

(def canonical-point
  {:id       s/Num
   :name     s/Str
   :capacity s/Num
   :address  w.o.address/Address})

(s/defschema Point
  (schema/strict-schema canonical-point))

(s/defschema Points
  (schema/strict-schema
    {:data [Point]}))