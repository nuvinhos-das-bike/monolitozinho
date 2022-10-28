(ns modulo-3.interceptors
  (:require [io.pedestal.interceptor :as i]))

(def all-points-interceptor
  (i/interceptor {:name  :all-points-interceptor
                  :enter (fn [context]
                           (let [db (:db context)]
                             (assoc context :response {:status 200
                                                       :body   (get @db :points)})))}))