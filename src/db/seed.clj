(ns db.seed
  (:import (java.util UUID)))

(defn uuid []
  (UUID/randomUUID))


(def rua-da-felicidade {:db/id                "rua-da-felicidade"
                        :address/id           (uuid)
                        :address/street       "rua da felicidade"
                        :address/number       "12a"
                        :address/zip-code     "72000000"
                        :address/address-line ""})

(def ponto-da-felicidade {:point/id       (uuid)
                          :point/name     "ponto da felicidade"
                          :point/capacity 10
                          :point/address  "rua-da-felicidade"
                          :point/bikes    [{:bike/id (uuid)}
                                           {:bike/id (uuid)}]})

(def ponto-da-euforia {:point/id       (uuid)
                       :point/name     "ponto da euforia"
                       :point/capacity 50
                       :point/address  "rua-da-felicidade"
                       :point/bikes    [{:bike/id (uuid)}]})

(def user-admin {:user/id (uuid)
                 :user/login "admin"
                 :user/subscriber true
                 :user/key "/&[3-.wff@qx'{aTX-2P>}XE_B6Jc+"})

(def user-user {:user/id (uuid)
                 :user/login "user"
                 :user/subscriber true
                 :user/key "in%^Ha[5mkNuJoARAjqN!'?RFlG[80"
                 :user/bike {:bike/id (uuid)}})

(def user-anon {:user/id (uuid)
                 :user/login "anon"
                 :user/subscriber false
                 :user/key "{{Uo~yG#E&#c6GOaS-!84b.Gi8OM\"H"})
