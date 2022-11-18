(ns server
  (:require [com.stuartsierra.component :as component]
            [io.pedestal.http :as http]
            [io.pedestal.test :as test]
            [io.pedestal.interceptor :as i]))

(defonce server (atom nil))

(defn start-server [service-map]
  (reset! server (http/start (http/create-server service-map))))

(defn test-request [verb url]
  (test/response-for (::http/service-fn @server) verb url))

(defn stop-server []
  (http/stop @server))

(defn restart-server [service-map]
  (stop-server)
  (start-server service-map))

(defrecord Servidor [database routes config]
  component/Lifecycle

  (start [this]
    (println "Start servidor")
    (let [assoc-store (fn [context]
                        (assoc-in context [:request :db-conn] (:conn database)))
          db-interceptor {:name  :db-interceptor
                          :enter assoc-store}
          error-interceptor {:error
                             (fn [ctx ex]
                               (if-let [cause (:cause (ex-data ex))]
                                 (assoc ctx :response {:status (get (ex-data ex) :status 400) :body cause})
                                 (assoc ctx :io.pedestal.interceptor.chain/error ex)))}
          service-map-base {::http/routes        (:routes routes)
                            ::http/port          (-> config :config :port)
                            ::http/resource-path "/resources/public"
                            ::http/type          :jetty
                            ::http/join?         false}
          service-map (-> service-map-base
                          (http/default-interceptors)
                          (update ::http/interceptors conj
                                  (i/interceptor error-interceptor)
                                  (i/interceptor db-interceptor)
                                                 http/json-body))]
      (try
        (start-server service-map)
        (println "Server Started successfully!")
        (catch Exception e
          (println "Error executing server start: " (.getMessage e))
          (println "Trying server restart..." (.getMessage e))
          (try
            (restart-server service-map)
            (println "Server Restarted successfully!")
            (catch Exception e (println "Error executing server restart: " (.getMessage e))))))
      (assoc this :test-request test-request)))

  (stop [this]
    (stop-server)))

(defn new-servidor []
  (->Servidor {} {} {}))