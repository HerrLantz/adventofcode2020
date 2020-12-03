(ns solution3.core
  (:require [clojure.string :as str]))

(defn parse-input
  [input]
  (-> (slurp input)
      (str/split #"\n")))

(defn is-tree?
  [possible-tree]
  (= possible-tree \#))

(defn count-trees
  [tree-map slope]
  (let [end-of-map-x (count (first tree-map))
        end-of-map-y (count tree-map)]
    (loop [nr-of-trees 0
           x           0
           y           0]
      (let [current-square  (as-> (nth tree-map y) $
                                  (nth $ x))
            new-nr-of-trees (if (is-tree? current-square)
                              (inc nr-of-trees)
                              nr-of-trees)
            new-x           (mod (+ x (:x slope)) end-of-map-x)
            new-y           (+ new-y (:y slope))]
        (if (>= new-y end-of-map-y)
          new-nr-of-trees
          (recur new-nr-of-trees new-x new-y))))))

(defn travel
  []
  (-> (parse-input "input")
      (count-trees {:y 1 :x 3})))

(defn travel2
  []
  (as-> (parse-input "input") $
        (map * (map count-trees $ [{:y 1 :x 1}
                                   {:y 3 :x 1}
                                   {:y 5 :x 1}
                                   {:y 7 :x 1}
                                   {:y 1 :x 2}]))))
