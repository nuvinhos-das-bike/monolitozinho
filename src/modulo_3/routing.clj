(ns modulo-3.routing
  (:require [io.pedestal.http.route :as route]
            [com.stuartsierra.component :as component]))

(def routes
  (route/expand-routes
    #{["/hello" :get (fn [request] {:status 200 :body "hello world"}) :route-name :hello]}))

(defrecord Routes []
  component/Lifecycle
  (start [this]
    (assoc this :routes routes))
  (stop [this]
    (dissoc this :routes))
  )

(defn new-routes []
  (->Routes))