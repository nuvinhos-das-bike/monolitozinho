(ns service
  (:require [io.pedestal.http.route :as route]
            [com.stuartsierra.component :as component]
            [diplomat.http-in :as d.http-in]
            [common-io.interceptors.errors :as errors]
            [common-io.interceptors.logging :as logging]))

(def common-interceptors
  [(errors/catch-externalize)
   (errors/catch)
   (logging/log)])

(def routes
  (route/expand-routes
    #{["/points" :get (conj common-interceptors
                            d.http-in/get-all-points) :route-name :all-points]}))

(defrecord Routes []
  component/Lifecycle

  (start [this]
    (assoc this :routes routes))

  (stop [this]
    (dissoc this :routes)))

(defn new-routes []
  (->Routes))