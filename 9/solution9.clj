(ns solution9.core
  (:require [clojure.string :as str]))

(defn parse-input
  [input]
  (as-> (slurp input) $
        (str/split $ #"\n")
        (map #(Long/valueOf %) $)))

(defn number-is-non-sum?
  [number preamble]
  (loop [pre preamble]
    (cond
      (empty? pre)                             true
      (some #(= number (+ % (first pre))) pre) false
      :else                                    (recur (rest pre)))))

(defn find-non-sum
  ([] (find-non-sum (parse-input "input")))
  ([data]
   (loop [preamble (take 25 data)
          numbers  (drop 25 data)]
     (if (number-is-non-sum? (first numbers) preamble)
       (first numbers)
       (recur (conj preamble (first numbers)) (rest numbers))))))

(defn sub-list
  [beginning end lst]
  (take (- (inc end) beginning)
        (drop beginning lst)))

(defn find-contigous-set
  []
  (let [data    (parse-input "input")
        non-sum (find-non-sum data)]
    (loop [beginning 0
           end       0]
      (let [sum (reduce + (sub-list beginning end data))]
        (cond
          (> sum non-sum) (recur (inc beginning) end)
          (< sum non-sum) (recur beginning (inc end))
          :else           (as-> (sort (sub-list beginning end data)) $
                                (+ (first $) (last $))))))))
