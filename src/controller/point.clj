(ns controller.point
  (:require [db.point :as d.point]
            [db.bike :as d.bike]
            [adapter.point :as a.point]
            [schema.core :as s]))

(defn get-all-points
  [db]
  (let [points (d.point/get-all-points db)
        bikes (d.bike/get-all-bikes db)]
     (a.point/all-points->wire points bikes)))

(s/defn get-point
  [point-id :- s/Keyword
   db]
  (d.point/get-point point-id db))