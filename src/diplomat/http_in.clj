(ns diplomat.http-in
  (:require (controller.point :as c.point)))

(defn get-all-points
  [{db :db}]
  {:status 200
   :body (c.point/get-all-points @db)})