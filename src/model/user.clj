(ns model.user
  (:require [schema.core :as s]
            [model.bike]))

(def skeleton-user
  {:id             s/Uuid
   :login          s/Str
   :key            s/Str
   :subscriber     s/Bool
   (s/maybe :bike) model.bike/Bike})

(s/defschema User skeleton-user)

(s/defschema Users [skeleton-user])