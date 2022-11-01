(ns wire.out.bike
  (:require [schema.core :as s]))

(def canonical-bike
  {:id                     s/Keyword
   (s/optional-key :point) s/Keyword
   (s/optional-key :user)  s/Keyword})

(s/defschema Bike
  canonical-bike)

(s/defschema Bikes
  [Bike])
