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

(def Bike {(s/optional-key :point) s/Num
           (s/optional-key :user)  s/Num})

(def User {:login                s/Str
           (s/optional-key :key) s/Str})

(def Points {s/Keyword Point})

(def Database
  {:points {s/Keyword Point}
   :bikes  {s/Keyword Bike}
   :users  {s/Keyword User}})
