(ns modulo-3.interceptors
  (:require [io.pedestal.interceptor :as i]
            [modulo-3.logic :as l]))

(def all-points-interceptor
  (i/interceptor {:name  :all-points-interceptor
                  :enter (fn [context]
                           (let [db (:db context)]
                             (assoc context :response {:status 200
                                                       :body   (l/all-points @db)})))}))

(def get-point-interceptor
  (i/interceptor {:name  :get-point
                  :enter (fn
                           [{:keys [request db] :as context}]
                           (let [point-id (get-in request [:path-params :id])]
                             (update context :request #(assoc % :db @db :point-id point-id))))}))
