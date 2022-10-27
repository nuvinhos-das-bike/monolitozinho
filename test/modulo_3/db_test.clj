(ns modulo-3.db-test
  (:require [clojure.test :refer :all]
            [modulo-3.db :refer :all]))

(def db-test (atom
               {:points {:1 {:name     "ponto da felicidade"
                             :capacity 10
                             :address  {:street       "rua da felicidade"
                                        :number       "12a"
                                        :zip-code     "72000000"
                                        :address-line ""}}
                         :2 {:name     "ponto da euforia"
                             :capacity 50
                             :address  {:street       "rua da felicidade"
                                        :number       "12a"
                                        :zip-code     "72000000"
                                        :address-line ""}}}
                :bikes  {:1 {:point 1
                             :user  nil}
                         :2 {:point 1
                             :user  nil}
                         :3 {:point 2
                             :user  nil}
                         :4 {:point nil
                             :user 1}}
                :users  {:1 {:login      "admin"
                             :subscribed true
                             :key        "/&[3-.wff@qx'{aTX-2P>}XE_B6Jc+"}}}))

