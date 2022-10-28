(ns modulo-3.schemas
  (:require [schema.core :as s]))

(def Address
  {:street       s/Str
   :number       s/Str
   :zip-code     s/Str
   :address-line s/Str})

(def Point
  {:name     s/Str
   :capacity s/Num
   :address  Address})

(def Bike {:point s/Num
           :user  (s/pred (fn [v] (or (nil? v) (number? v))) 'nil-or-number?)})

(def Database
  {:points {s/Keyword Point}
   :bikes  {s/Keyword Bike}
   :users  -})
