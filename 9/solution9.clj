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
      (empty? pre)                                  true
      (some #(= number (+ % (first pre))) preamble) false
      :else                                         (recur (rest preamble)))))

(defn find-non-sum
  []
  (as-> (parse-input "input") data
        (loop [preamble (take 25 data)
               numbers  (drop 25 data)]
          (println (str "---- " (first  numbers) " ----"))
          (if (number-is-non-sum? (first numbers) preamble)
            (first numbers)
            (recur (conj preamble (first numbers)) (rest numbers))))))
