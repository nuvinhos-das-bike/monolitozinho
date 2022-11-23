(ns controller.point
  (:require [db.point :as d.point]
            [schema.core :as s]))

(defn get-all-points
  [db-conn]
  (d.point/get-all-points db-conn))

(s/defn get-point
  [point-id :- s/Uuid
   db-conn]
  (d.point/get-point point-id db-conn))