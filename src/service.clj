(ns service
  (:require [io.pedestal.http.route :as route]
            [com.stuartsierra.component :as component]
            [modulo-3.interceptors :as i]
            [diplomat.http-in :as d.http-in]))

(def common-interceptors
  [])

(def routes
  (route/expand-routes
    #{["/points" :get (conj common-interceptors
                            i/all-points-interceptor
                            d.http-in/get-all-points) :route-name :all-points]}))

(defrecord Routes []
  component/Lifecycle

  (start [this]
    (assoc this :routes routes))

  (stop [this]
    (dissoc this :routes)))

(defn new-routes []
  (->Routes))