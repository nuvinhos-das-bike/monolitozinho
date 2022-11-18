(ns adapter.point
  (:require [wire.out.point :as w.o.point]
            [wire.out.bike :as w.o.bike]
            [model.point :as m.point]
            [schema.core :as s]
            [model.bike :as m.bike]))

(s/defn all-bikes->bikes-with-id :- w.o.bike/Bikes
  [bikes :- m.bike/Bikes]
  (mapv (fn [[id data]]
          (merge {:id id} data)) bikes))

(s/defn all-points->wire :- w.o.point/Points
  [points :- m.point/Points
   bikes :- m.bike/Bikes]
  (let [bikes (all-bikes->bikes-with-id bikes)]

     (->> (map (fn [[id data]]
                        (merge {:id id} data)) points)

                 (mapv (fn [{:keys [id] :as point}]
                         (assoc point :bikes
                                      (filterv (fn [{point-id :point}]
                                                 (= point-id id)) bikes)))))))

(defn datomic->points [points bikes])
