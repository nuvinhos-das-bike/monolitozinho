(ns db.point
  (:require [model.point :as model.point]
            [schema.core :as s]))

(s/defn get-all-points [db] :- model.point/Points
  (get @db :points))

(s/defn get-point :- model.point/Point
  [id :- s/Keyword
   db]
  (get-in @db [:points id]))