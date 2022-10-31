(ns modulo-3.routing
  (:require [io.pedestal.http.route :as route]
            [com.stuartsierra.component :as component]
            [modulo-3.interceptors :as i]))

(def routes
  (route/expand-routes
    #{["/hello" :get (fn [request] {:status 200 :body "hello world"}) :route-name :hello]
      ["/bikes/:id-bike/points/:id-point" :patch [i/authorize-user i/validate-bike i/validate-user-has-bike i/validate-point i/handle-bike-devolution] :route-name :handle-bike-devolution]
      ["/bikes/:id-bike/users/:id-user" :patch [i/authorize-user i/validate-bike i/handle-bike-request] :route-name :handle-bike-request]}))

(defrecord Routes []
  component/Lifecycle
  (start [this]
    (assoc this :routes routes))
  (stop [this]
    (dissoc this :routes))
  )

(defn new-routes []
  (->Routes))
