(ns modulo-3.routing
  (:require [io.pedestal.http.route :as route]
            [com.stuartsierra.component :as component]
            [modulo-3.interceptors :as i]
            [modulo-3.logic :as logic]))

(def routes
  (route/expand-routes
    #{["/hello" :get (fn [request] {:status 200 :body "hello world"}) :route-name :hello]
      ["/bikes/:id-bike/users/:id-user" :post [i/user-exist logic/retirada-bike] :route-name :retirada]}))

(defrecord Routes []
  component/Lifecycle
  (start [this]
    (assoc this :routes routes))
  (stop [this]
    (dissoc this :routes))
  )

(defn new-routes []
  (->Routes))
