(ns db.schema)


(def address-schema [{:db/ident       :address/id
                      :db/valueType   :db.type/uuid
                      :db/cardinality :db.cardinality/one
                      :db/unique      :db.unique/identity}

                     {:db/ident       :address/street
                      :db/valueType   :db.type/string
                      :db/cardinality :db.cardinality/one}

                     {:db/ident       :address/number
                      :db/valueType   :db.type/string
                      :db/cardinality :db.cardinality/one}

                     {:db/ident       :address/zip-code
                      :db/valueType   :db.type/string
                      :db/cardinality :db.cardinality/one}

                     {:db/ident       :address/address-line
                      :db/valueType   :db.type/string
                      :db/cardinality :db.cardinality/one}])

(def point-schema [{:db/ident       :point/id
                    :db/valueType   :db.type/uuid
                    :db/cardinality :db.cardinality/one
                    :db/unique      :db.unique/identity}

                   {:db/ident       :point/name
                    :db/valueType   :db.type/string
                    :db/cardinality :db.cardinality/one}

                   {:db/ident       :point/capacity
                    :db/valueType   :db.type/long
                    :db/cardinality :db.cardinality/one}

                   {:db/ident       :point/address
                    :db/valueType   :db.type/ref
                    :db/cardinality :db.cardinality/one}

                   {:db/ident       :point/bikes
                    :db/valueType   :db.type/ref
                    :db/cardinality :db.cardinality/many}])

(def bike-schema [{:db/ident       :bike/id
                   :db/valueType   :db.type/uuid
                   :db/cardinality :db.cardinality/one
                   :db/unique      :db.unique/identity}])

(def user-schema [{:db/ident       :user/id
                   :db/valueType   :db.type/uuid
                   :db/cardinality :db.cardinality/one
                   :db/unique      :db.unique/identity}

                  {:db/ident       :user/login
                   :db/valueType   :db.type/string
                   :db/cardinality :db.cardinality/one}

                  {:db/ident       :user/subscriber
                   :db/valueType   :db.type/boolean
                   :db/cardinality :db.cardinality/one}

                  {:db/ident       :user/key
                   :db/valueType   :db.type/string
                   :db/cardinality :db.cardinality/one
                   :db/unique      :db.unique/identity}

                  {:db/ident       :user/bike
                   :db/valueType   :db.type/ref
                   :db/cardinality :db.cardinality/one}])



