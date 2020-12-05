(ns solution1.core
  (:require [clojure.string :as str]))

(defn parse-input
  [file]
  (as-> (slurp file) $
        (str/split $ #"\n")
        (map (fn [x] (Integer. x)) $)))

(defn first-star
  []
  (let [input (parse-input "input")]
    (reduce (fn [acc curr] (if (some #(= % (- 2020 curr)) input)
                             (* curr (- 2020 curr)) acc)) input)))

(defn second-star
  []
  (let [input       (parse-input "input")
        numbers-map (reduce (fn [acc curr] (conj acc {curr true}))
                            {}
                            input)]
    (loop [l1 input]
      (or (loop [l2 l1]
            (let [n1 (first l1)
                  n2 (first l2)]
              (cond
                (empty? l2)                          nil
                (get numbers-map (- 2020 (+ n1 n2))) (* (- 2020 (+ n1 n2)) n1 n2)
                :else                                (recur (rest l2)))))
          (recur (rest l1))))))

