(ns modulo-3.interceptors
  (:require [io.pedestal.interceptor :as i]
            [modulo-3.logic :as l]))

(def all-points-interceptor
  (i/interceptor {:name  :all-points-interceptor
                  :enter (fn [context]
                           (let [db (:db context)]
                             (assoc context :response {:status 200
                                                       :body   (l/all-points @db)})))}))