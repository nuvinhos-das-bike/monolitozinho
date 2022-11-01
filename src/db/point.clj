(ns db.point
  (:require [model.database :as m.database]
            [model.point :as m.point]
            [schema.core :as s]))

(s/defn ^:always-validate get-all-points [db :- m.database/Database] :- m.point/Points
        (get db :points))