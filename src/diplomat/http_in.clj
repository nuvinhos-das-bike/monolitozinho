(ns diplomat.http-in
  (:require [controller.point :as controller.point]
            [controller.bike :as controller.bike]))

(defn get-all-points
  [{db-conn :db-conn}]
  {:status 200
   :body   {:data (controller.point/get-all-points db-conn)}})

(defn get-point
  [{db-conn             :db-conn
    {point-id :id} :path-params}]
  (let [point (controller.point/get-point #uuid point-id db-conn)]
    (if (not (nil? point))
      {:status 200
       :body   point}
      {:status 404})))

(defn request-bike
  [{id-bike :id-bike
    id-user :id-user
    db      :db}]
  (controller.bike/request-bike id-bike id-user db)
  {:status 200
   :body   "Bike Requested"})

(defn return-bike
  [{id-bike  :id-bike
    id-point :id-point
    db       :db}]
  (controller.bike/return-bike id-bike id-point db)
  {:status 200
   :body "Bike returned"})