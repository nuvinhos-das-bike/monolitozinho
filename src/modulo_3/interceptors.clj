(ns modulo-3.interceptors
  (:require [io.pedestal.interceptor :as i]))

(def user-exist
  (i/interceptor {:name  :user-exist
                  :enter (fn [{:keys [db request] :as context}]
                           (let [id-user (-> request :path-params :id-user keyword)
                                 users (:users @db)]
                             ;(println "Intercepted")
                             ;(println id-user)
                             ;(println users)
                             ;(println (id-user users))
                             ;(println (nil? (id-user users)))
                             ;(println context)
                             (assoc context :response (if (nil? (id-user users))
                                                        {:status 401
                                                         :body "Usuário não Existente"}
                                                        {:status 200
                                                         :body   id-user}))))}))
