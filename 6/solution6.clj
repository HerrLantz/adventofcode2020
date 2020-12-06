(ns solution6.core
  (:require [clojure.string :as str]
            [clojure.set    :as set]))

(defn parse-input [input] (str/split (slurp input) #"\n\n"))

(defn count-answers [] (reduce + (map (fn [a] (count (remove #(= \newline %) (set a)))) (parse-input "input"))))

