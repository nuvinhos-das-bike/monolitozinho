(ns model.point
  (:require [schema.core :as s]
            [model.address :as m.address]))

(def canonical-point
  {:name     s/Str
   :capacity s/Num
   :address  m.address/Address})

(s/defschema Point
   canonical-point)

(s/defschema Points
  {s/Keyword Point})