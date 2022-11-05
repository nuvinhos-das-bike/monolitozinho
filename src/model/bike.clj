(ns model.bike
  (:require [schema.core :as s]
            [common-core.schema :as schema]))

(def skeleton-bike
  {:point {:schema s/Keyword :require false}
   :user  {:schema s/Keyword :require false}})

(s/defschema Bike
  (schema/strict-schema skeleton-bike))

(s/defschema Bikes
  (schema/strict-schema {s/Keyword Bike}))