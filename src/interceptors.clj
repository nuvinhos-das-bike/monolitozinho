(ns interceptors
  (:require [io.pedestal.interceptor :as i]
            [db.user :as db.user]
            [db.bike :as db.bike]))

(def authorize-user
  (i/interceptor {:name  :authorize-user
                  :enter (fn [context]
                           (if-let [api-key (get (-> context :request :headers) "api-key")]
                             (let [user (->> context :request :db-conn (db.user/get-user-by-key api-key))]
                               (if user
                                 (assoc-in context [:request :user] user)
                                 (throw (ex-info "User forbidden" {:cause "not-allowed" :status 403}))))
                             (throw (ex-info "Need a api-key" {:cause "need-api-key" :status 401}))))}))

(def validate-bike
  (i/interceptor {:name  :validate-bike
                  :enter (fn [{{{id-bike :id-bike} :path-params
                                db-conn            :db-conn} :request :as context}]
                           (if-let [bike (db.bike/get-bike (parse-uuid id-bike) db-conn)]
                             (assoc-in context [:request :id-bike] (:id bike))
                             (throw (ex-info "Bike not exists" {:cause "bike-not-exists"}))))}))

(def validate-user-has-bike
  (i/interceptor {:name  :validate-user-has-bike
                  :enter (fn [{{authorized-user :user
                                id-bike         :bike
                                conn            :db-conn} :request :as context}]
                           (let [bike-user (db.bike/get-from-user authorized-user conn)]
                             (if (= (parse-uuid id-bike) (:bike/id bike-user))
                               context
                               (throw (ex-info "User can't iterate with this bike" {:cause "user-not-has-bike"})))))}))

(def validate-point
  (i/interceptor {:name  :validate-bike
                  :enter (fn [{{{id-point :id-point} :path-params
                                db                   :db-conn} :request :as context}]
                           (if (-> id-point parse-uuid (db.point/get-point db))
                             (assoc-in context [:request :point] id-point)
                             (throw (ex-info "Point not exists" {:cause "point-not-exists"}))))}))