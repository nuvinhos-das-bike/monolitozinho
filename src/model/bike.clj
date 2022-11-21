(ns model.bike
  (:require [schema.core :as s]))

(def skeleton-bike
  {:point {:schema s/Keyword :require false}
   :user  {:schema s/Keyword :require false}})

(s/defschema Bike skeleton-bike)

(s/defschema Bikes {s/Keyword Bike})