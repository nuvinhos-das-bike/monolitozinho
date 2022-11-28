(ns diplomat.http-in
  (:require [controller.point :as controller.point]
            [controller.bike :as controller.bike]
            [controller.user])
  (:import (java.util UUID)))

(defn get-all-points
  [{db-conn :db-conn}]
  {:status 200
   :body   (controller.point/get-all-points db-conn)})

(defn get-point
  [{db-conn             :db-conn
    {point-id :id} :path-params}]
  (let [point (controller.point/get-point (UUID/fromString point-id) db-conn)]
    (if (not (nil? point))
      {:status 200
       :body   point}
      {:status 404})))

(defn request-bike
  [{id-bike :id-bike
    user :user
    db-conn      :db-conn}]
  (controller.bike/request-bike id-bike (:id user) db-conn)
  {:status 200
   :body   "Bike Requested"})

(defn return-bike
  [{id-bike  :id-bike
    id-point :point
    user     :user
    conn     :db-conn}]
  (controller.bike/return-bike id-bike (parse-uuid id-point) (:id user) conn)
  {:status 200
   :body "Bike returned"})

(defn get-user
  [{db-conn             :db-conn
    {user-id :id} :path-params}]
  (let [point (controller.user/get-user (UUID/fromString user-id) db-conn)]
    (if (not (nil? point))
      {:status 200
       :body   point}
      {:status 404})))

(defn get-users
  [{db-conn             :db-conn}]
  (let [point (controller.user/get-users db-conn)]
    {:status 200
     :body   point}))