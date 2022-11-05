(ns model.address
  (:require [schema.core :as s]
            [common-core.schema :as schema]))

(def skeleton-address
  {:street       s/Str
   :number       s/Str
   :zip-code     s/Str
   :address-line s/Str})

(s/defschema Address
  (schema/strict-schema skeleton-address))