(ns model.database
  (:require [schema.core :as s]
            [model.point :as model.point]
            [model.bike :as model.bike]
            [model.user :as model.user]))

(def canonical-database
  {:points {s/Keyword model.point/Point}
   :bikes  {s/Keyword model.bike/Bike}
   :users  {s/Keyword model.user/User}})

(s/defschema Database
  canonical-database)