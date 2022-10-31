(ns modulo-3.interceptors
  (:require [io.pedestal.interceptor :as i]
            [modulo-3.logic :as l]))

(def all-points-interceptor
  (i/interceptor {:name  :all-points-interceptor
                  :enter (fn [context]
                           (let [db (:db context)]
                             (assoc context :response {:status 200
                                                       :body (l/all-points @db)})))}))
(def validate-bike
  (i/interceptor {:name  :validate-bike
                  :enter (fn [context]
                           (let [id-bike (-> context :request :path-params :id-bike)]
                             (try
                               (Integer/parseInt id-bike)
                               (catch NumberFormatException _ (throw (ex-info "Invalid bike number" {:cause "number-invalid"}))))
                             (if (-> context :db deref :bikes (get (keyword id-bike)))
                               (assoc context :id-bike (keyword id-bike))
                               (throw (ex-info "Bike not exists" {:cause "bike-not-exists" })))))}))

(def validate-point
  (i/interceptor {:name  :validate-bike
                  :enter (fn [context]
                           (let [id-point (-> context :request :path-params :id-point)]
                             (try
                               (Integer/parseInt id-point)
                               (catch NumberFormatException _ (throw (ex-info "Invalid point number" { :cause "point-number-invalid" }))))
                             (if (-> context :db deref :points (get (keyword id-point)))
                               (assoc context :id-point (keyword id-point))
                               (throw (ex-info "Point not exists" {:cause "point-not-exists"})))))}))

(def validate-user-has-bike
  (i/interceptor {:name  :validate-user-has-bike
                  :enter (fn [context]
                           (let [authorized-user (:id-user context)
                                 bike-user (-> context :db deref :bikes (get (:id-bike context)) :user)]
                             (if (= authorized-user bike-user)
                               context
                               (throw (ex-info "User can't iterate with this bike" {:cause "user-not-has-bike" })))))}))

(def validate-user-exists
  (i/interceptor {:name  :validate-user-exists
                  :enter (fn [context]
                           (let [user (-> context :request :path-params :id-user keyword)
                                 users-db (-> context :db deref :users)]
                             (if (get users-db user)
                               context
                               (throw (ex-info "user-not-exists" {:cause "user-not-exists"}})))))}))

                              

(def authorize-user
  (i/interceptor {:name  :authorize-user
                  :enter (fn [context]
                           (if-let [api-key (get (-> context :request :headers) "api-key")]
                             (let [user (->> context :db deref (l/get-user-by-key api-key))]
                               (if user
                                 (assoc context :id-user (:id user))
                                 (throw (ex-info "User forbidden" { :cause "not-allowed" :status 403 }))))
                             (throw (ex-info "Need a api-key" { :cause "need-api-key" :status 401 }))))}))

(def handle-bike-devolution
  (i/interceptor {:name  :handle-bike-devolution
                  :enter (fn [{id-bike  :id-bike
                               id-point :id-point
                               db       :db
                               :as      context}]
                           (reset! db (l/bike-devolution id-bike id-point @db))
                           (assoc context :response {:status 200}))}))


(def handle-bike-request
  (i/interceptor {:name  :handle-bike-devolution
                  :enter (fn [{id-bike  :id-bike
                               id-user :id-user
                               db       :db
                               :as      context}]
                           (reset! db (l/bike-request id-bike id-user @db))
                           (assoc context :response {:status 200
                                                     :body "Bike Requested"}))}))

(def handle-subscription
  (i/interceptor {:name  :handle-user-subscription
                  :enter (fn [{id-user :id-user db :db :as context}]
                           (let [subscription (-> context :db deref :users (get id-user) :subscriber)]
                             (if (not subscription)
                               (do
                                 (reset! db (l/subscribe-user id-user @db))
                                 (assoc context :response {:status 200}))
                               (throw (ex-info "User already have a subscription" { :cause "already-subscribed" })))))}))
