(ns model.point
  (:require [schema.core :as s]
            [model.address :as m.address]
            [common-core.schema :as schema]
            [model.bike]))

(def skeleton-point
  {:name     s/Str
   :capacity s/Num
   :address  m.address/Address})

(s/defschema Point
  (schema/strict-schema skeleton-point))

(s/defschema Points
  (schema/strict-schema {s/Keyword Point}))

(s/defschema PointWithBikes
  (schema/strict-schema (assoc skeleton-point
                          :bikes [{s/Keyword model.bike/Bike}])))

(s/defschema PointsWithBikes
  (schema/strict-schema [(assoc skeleton-point
                           :bikes [{s/Keyword model.bike/Bike}])]))