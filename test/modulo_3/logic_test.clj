(ns modulo-3.logic-test
  (:require [clojure.test :refer :all]
            [modulo-3.logic :refer :all])
  (:import (clojure.lang ExceptionInfo)))

(def db (atom {:users {:1 {} :2 {}}
               :bikes {:1 {:point :1} :2 {:user :2}}}))

(deftest retirada-bike-test
  (testing "Retrieve bike successfully"
    (is (= (retirada-bike :1 :1 @db) {:users {:1 {}}
                                      :bikes {:user :1}})))

  ;; TODO testes ainda podem ser melhorados
  (testing "Bike with another user"
    (is (thrown? Exception (retirada-bike :2 :1 @db))))

  (testing "Usuário já possui bike vinculada"
    (is (thrown? Exception (retirada-bike :1 :2 @db)))))

(deftest bike-devolution-test
  (testing "Bike devolution succesfully"
    (let [db {:points {:1 {:capacity 1}}
              :bikes  {:2 {:user :3}}}
          expected {:points {:1 {:capacity 1}}
                    :bikes  {:2 {:point :1}}}
          actual (bike-devolution :2 :1 db)]
      (is (= actual expected)))
    (let [db {:points {:bar {:capacity 50}}
              :bikes  {:foo {:user :foobar}}}
          expected {:points {:bar {:capacity 50}}
                    :bikes  {:foo {:point :bar}}}
          actual (bike-devolution :foo :bar db)]
      (is (= actual expected))))

  (testing "Exceed point capacity"
    (are [db] (thrown-with-msg? ExceptionInfo #"point-full" (bike-devolution :2 :1 db))
              {:points {:1 {:capacity 1}}
               :bikes  {:2 {:user :3} :4 {:point :1}}}
              {:points {:1 {:capacity 1}}
               :bikes  {:2 {:user :3} :4 {:point :1} :5 {:point :1}}})))

(deftest has-it-capacity?-test
  (let [mapa {:points {:1 {:capacity 1} :2 {:capacity 1}}
              :bikes  {:2 {:user :3} :4 {:point :1}}}]
    (testing "Full"
      (is (= (has-it-capacity? mapa :1) false)))
    (testing "Has space"
      (is (= (has-it-capacity? mapa :2) true)))))