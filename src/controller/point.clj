(ns controller.point
  (:require [db.point :as d.point]
            [db.bike :as d.bike]
            [adapter.point :as a.point]))

(defn get-all-points
  [db]
  (let [points (d.point/get-all-points db)
        bikes (d.bike/get-all-bikes db)]
     (a.point/all-points->wire points bikes)))