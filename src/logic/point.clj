(ns logic.point
  (:require [schema.core :as s]))

(s/defn point-available? :- s/Bool
  [{capacity           :capacity
    all-bikes-in-point :bikes} :- model.point/PointWithBikes]
  (< (count all-bikes-in-point) capacity))
