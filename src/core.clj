(ns core
  (:require [com.stuartsierra.component :as component]
            [config :as config]
            [service :as routes]
            [db.db :as db]
            [server :as web-server]
            [schema.core :as s])
  (:gen-class))

(s/set-fn-validation! false)

(def new-sys
  (component/system-map
    :config (config/new-config)
    :routes (routes/new-routes)
    :database (component/using
                (db/new-database)
                [:config])
    :web-server (component/using
                  (web-server/new-servidor)
                  [:database :routes :config])))
(def sys (atom nil))
(defn main [] (reset! sys (component/start new-sys)))

(comment
  (main)
  )