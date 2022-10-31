(ns controller.point
  (:require (db.point :as db.point)))

(defn get-all-points
  [db]
  (db.point/get-all-points db))