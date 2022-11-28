(ns model.bike
  (:require [schema.core :as s]))

(def skeleton-bike
  {:id s/Uuid})

(s/defschema Bike skeleton-bike)

(s/defschema Bikes [Bike])