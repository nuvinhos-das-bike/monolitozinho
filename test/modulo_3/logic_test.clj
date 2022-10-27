(ns modulo-3.logic-test
  (:require [clojure.test :refer :all]
            [modulo-3.logic :refer :all]))

(def db (atom {:users {1 {} 2 {}}
               :bikes {1 {:point 1} 2 {:user 2}}}))

(deftest retirada-bike-test
  (testing "Retirada da bike foi um sucesso"
    (is (= {:users {1 {}}
            :bikes {:user 1}} (retirada-bike 1 1 @db))))

  ;; ih rapaz, ver como testa o erro
  (testing "Bike ta com outro usuario"
    (retirada-bike 2 1 @db))

  (testing "Usuário já possui bike vinculada"
    (retirada-bike 1 2 @db)))