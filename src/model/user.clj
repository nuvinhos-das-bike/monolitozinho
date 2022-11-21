(ns model.user
  (:require [schema.core :as s]))

(def skeleton-user
  {:login s/Str
   :key   s/Str
   :subscriber s/Bool})

(s/defschema User skeleton-user)