(ns modulo-3.routing
  (:require [io.pedestal.http.route :as route]
            [com.stuartsierra.component :as component]
            [clojure.pprint :as pprint]))

(def get-point-id-and-points
  {:name  ::get-point
   :enter (fn
            [{:keys [request db] :as context}]
            (let [path-params (-> request :path-params)
                  id (:id path-params)
                  points (-> @db :points)]
              (update context :request #(assoc % :points points
                                                 :id id))))})

(defn get-point
  [{:keys [points id]}]
  (let [point (get points (keyword (str id)))]
    {:status 200
     :body   (str point)}))

(def routes
  (route/expand-routes
    #{["/hello" :get (fn [request] {:status 200 :body "hello world"}) :route-name :hello]
      ["/points/:id"
       :get [get-point-id-and-points get-point]
       :route-name :get-point]}))

(defrecord Routes []
  component/Lifecycle
  (start [this]
    (assoc this :routes routes))
  (stop [this]
    (dissoc this :routes))
  )

(defn new-routes []
  (->Routes))