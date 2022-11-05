(ns model.point
  (:require [schema.core :as s]
            [model.address :as m.address]
            [common-core.schema :as schema]))

(def skeleton-point
  {:name     s/Str
   :capacity s/Num
   :address  m.address/Address})

(s/defschema Point
  (schema/strict-schema skeleton-point))

(s/defschema Points
  (schema/strict-schema {s/Keyword Point}))