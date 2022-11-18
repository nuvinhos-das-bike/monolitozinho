(ns controller.bike
  (:require [db.bike :as d.bike]
            [logic.point :as logic.point]))

(defn request-bike
  [id-bike id-user db]
  (d.bike/request-bike id-bike id-user db))

(defn return-bike
  [id-bike id-point db]
  (if (logic.point/point-available? (db.point/get-point id-point db))
    (d.bike/return-bike id-bike id-point db)
    (throw (ex-info "Point already reached capacity" {:cause "point-full"}))))