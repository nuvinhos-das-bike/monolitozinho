(ns model.user
  (:require [schema.core :as s]))

(def canonical-user
  {:login                s/Str
   (s/optional-key :key) s/Str})

(s/defschema User
  canonical-user)