(ns modulo-3.logic
  (:require [modulo-3.models :as m]
            [schema.core :as s]))

(s/defn ^:always-validate all-points [db :- m/Database] :- m/Points
  (get db :points))