(ns model.address
  (:require [schema.core :as s]))

(def skeleton-address
  {:id           s/Uuid
   :street       s/Str
   :number       s/Str
   :zip-code     s/Str
   :address-line s/Str})

(s/defschema Address skeleton-address)