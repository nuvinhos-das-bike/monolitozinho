(ns db.bike
  (:require [model.database :as m.database]
            [model.bike :as m.bike]
            [schema.core :as s]))

(s/defn ^:always-validate get-all-bikes [db :- m.database/Database] :- m.bike/Bikes
  (get db :bikes))
