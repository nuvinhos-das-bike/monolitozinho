(ns modulo-3.models
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

(def Bike {(s/optional-key :point) s/Keyword
           (s/optional-key :user)  s/Keyword})

(def User {:login      s/Str
           :subscriber s/Bool
           :key        s/Str})

(def Points {s/Keyword Point})

(def Database
  {:points {s/Keyword Point}
   :bikes  {s/Keyword Bike}
   :users  {s/Keyword User}})
