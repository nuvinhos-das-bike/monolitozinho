(ns modulo-3.logic-test
  (:require [clojure.test :refer :all]
            [modulo-3.logic :refer :all]
            [clj-http.client :as client])
  (:import (clojure.lang ExceptionInfo)))

(def db (atom {:users  {:1 {} :2 {}}
               :bikes  {:1 {:point :1} :2 {:user :2}}
               :points {:1 {:street "rua da felicidade"}
                        :2 {:street "rua da felicidade 2"}}}))

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
    (are [db] (try
                (bike-devolution :2 :1 db)
                (catch ExceptionInfo ex
                  (is (= (ex-data ex) { :cause "point-full" }))))
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

(deftest get-point-test
  (testing "get a existent point in db"
    (is (= (get-point @db 1) {:street "rua da felicidade"})))
  (testing "get a NOT existent point in db"
    (is (= (get-point @db 3) nil))))

(comment
  ;; esse trecho produz algum side effect?
  (deftest get-point-succeed-status?
    (testing "Retrieving a point by id success"
      (let [existent-id "1"
            expected-code 200
            actual-code (-> (str "http://localhost:9876/points/" existent-id)
                            (client/get)
                            :status)]
        (is (= expected-code actual-code) true))))
  )