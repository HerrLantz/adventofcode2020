(ns solution1.core
  (:require [clojure.string :as str]))

(defn get-input
  [file]
  (as-> (slurp file) $
        (str/split $ #"\n")
        (map (fn [x] (Integer. x)) $)))

(defn is-2020?
  [a b]
  (= (- 2020 (+ a b)) 0))

(defn first-star
  []
  (let [input (get-input "input")]
    (loop [l1 input]
      (or (loop [l2 l1]
            (let [n1 (first l1)
                  n2 (first l2)]
              (cond
                (empty? l2)       nil
                (is-2020? n1 n2) (* n1 n2)
                :else            (recur (rest l2)))))
          (recur (rest l1))))))

