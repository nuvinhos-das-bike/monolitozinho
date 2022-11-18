(ns model.user
  (:require [schema.core :as s]
            [common-core.schema :as schema]))

(def skeleton-user
  {:login s/Str
   :key   s/Str
   :subscriber s/Bool})

(s/defschema User
  (schema/strict-schema skeleton-user))