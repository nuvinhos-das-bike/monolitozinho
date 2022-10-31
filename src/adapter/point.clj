(ns adapter.point
  (:require [wire.out.point :as w.o.point]
            [model.point :as m.point]))

(defn all-points->wire :- w.o.point/Points
  [points :- m.point/Points]
  ;TODO Retornar no formato esperado
  {:data ...})