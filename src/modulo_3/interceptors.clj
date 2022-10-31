(ns modulo-3.interceptors
  (:require [io.pedestal.interceptor :as i]
            [modulo-3.logic :as l]
            [io.pedestal.interceptor.chain :as interceptor.chain]))

(def all-points-interceptor
  (i/interceptor {:name  :all-points-interceptor
                  :enter (fn [context]
                           (let [db (:db context)]
                             (assoc context :response {:status 200
                                                       :body (l/all-points @db)})))}))



(defn user-for-keyRequest [users key-user]
  (first (filter (fn [[_ user-data]]
                                       (= key-user (get user-data :key))) users)))



(def bind-key-interceptor
  (i/interceptor {:name  :bind-key-interceptor
                  :enter (fn [context]
                           (let [db (:db context)
                                 users (:users @db)
                                 key-user (get-in context [:request :headers "key"])
                                 [user-id] (user-for-keyRequest users key-user)]
                             (if user-id
                               (assoc context [:request :user] user-id)
                               (interceptor.chain/terminate (assoc context :response {:status 401
                                                         :body   "Unauthorized"})))))}))