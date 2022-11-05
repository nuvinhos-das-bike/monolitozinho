(ns db.point
  (:require [model.database :as m.database]
            [model.point :as m.point]
            [schema.core :as s]))

(s/defn ^:always-validate get-all-points [db :- m.database/Database] :- m.point/Points
  (get db :points)
  (throw (ex-info "Customer already exists"
                  {:type    :conflict
                   :details {:customer-id :1}})))