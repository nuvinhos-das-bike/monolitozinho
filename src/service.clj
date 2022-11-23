(ns service
  (:require [io.pedestal.http.route :as route]
            [com.stuartsierra.component :as component]
            [diplomat.http-in :as d.http-in]
            [interceptors :as i]))

(def common-interceptors
  [])

(def routes
  (route/expand-routes
    #{["/points"
       :get (conj common-interceptors
                  d.http-in/get-all-points)
       :route-name :all-points]

      ["/points/:id"
       :get (conj common-interceptors
                  d.http-in/get-point)
       :route-name :get-point]

      ["/bikes/request/:id-bike"
       :patch (conj common-interceptors
                    i/authorize-user
                    i/validate-bike
                    d.http-in/request-bike)
       :route-name :bike-request]

      ["/bikes/return/:id-bike/points/:id-point"
       :patch (conj common-interceptors
                    i/authorize-user
                    i/validate-bike
                    i/validate-user-has-bike
                    i/validate-point
                    d.http-in/return-bike)
       :route-name :handle-bike-devolution]

      ["/users"
       :get (conj common-interceptors
                  d.http-in/get-users)
       :route-name :get-users]

      ["/users/:id"
       :get (conj common-interceptors
                  d.http-in/get-user)
       :route-name :get-user]}))

(defrecord Routes []
  component/Lifecycle

  (start [this]
    (assoc this :routes routes))

  (stop [this]
    (dissoc this :routes)))

(defn new-routes []
  (->Routes))