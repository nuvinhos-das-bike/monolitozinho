(ns model.bike
  (:require [schema.core :as s]))

(def canonical-bike
  {(s/optional-key :point) s/Num
   (s/optional-key :user)  s/Num})

(s/defschema Bike
  canonical-bike)