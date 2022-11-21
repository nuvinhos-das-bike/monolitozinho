(ns model.point
  (:require [schema.core :as s]
            [model.address :as m.address]
            [model.bike]))

(def skeleton-point
  {:name     s/Str
   :capacity s/Num
   :address  m.address/Address})

(s/defschema Point skeleton-point)

(s/defschema Points {s/Keyword Point})

(s/defschema PointWithBikes (assoc skeleton-point
                                      :bikes [{s/Keyword model.bike/Bike}]))

(s/defschema PointsWithBikes [(assoc skeleton-point
                                       :bikes [{s/Keyword model.bike/Bike}])])