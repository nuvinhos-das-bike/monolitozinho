(ns interceptors
  (:require [io.pedestal.interceptor :as i]
            [db.user :as db.user]
            [db.bike :as db.bike]))

(def authorize-user
  (i/interceptor {:name  :authorize-user
                  :enter (fn [context]
                           (if-let [api-key (get (-> context :request :headers) "api-key")]
                             (let [user (->> context :request :db (db.user/get-user-by-key api-key))]
                               (if user
                                 (assoc-in context [:request :id-user] (:id user))
                                 (throw (ex-info "User forbidden" {:cause "not-allowed" :status 403}))))
                             (throw (ex-info "Need a api-key" {:cause "need-api-key" :status 401}))))}))

(def validate-bike
  (i/interceptor {:name  :validate-bike
                  :enter (fn [{{{id-bike :id-bike} :path-params
                                db                 :db} :request :as context}]
                           (if (db.bike/get-bike (keyword id-bike) db)
                             (assoc-in context [:request :id-bike] (keyword id-bike))
                             (throw (ex-info "Bike not exists" {:cause "bike-not-exists"}))))}))

(def validate-user-has-bike
  (i/interceptor {:name  :validate-user-has-bike
                  :enter (fn [{{authorized-user :id-user
                                id-bike         :id-bike
                                db              :db} :request :as context}]
                           (let [bike-user (-> (db.bike/get-bike (keyword id-bike) db)
                                               :user)]
                             (if (= authorized-user bike-user)
                               context
                               (throw (ex-info "User can't iterate with this bike" {:cause "user-not-has-bike"})))))}))

(def validate-point
  (i/interceptor {:name  :validate-bike
                  :enter (fn [{{{id-point :id-point} :path-params
                                db                   :db} :request :as context}]
                           (if (-> db.point/get-point (keyword id-point) db)
                             (assoc-in context [:request :id-point] (keyword id-point))
                             (throw (ex-info "Point not exists" {:cause "point-not-exists"}))))}))