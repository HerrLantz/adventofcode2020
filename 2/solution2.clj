(ns solution2.core
  (:require [clojure.string :as str]))

(defn parse-line
  [line]
  (let [[l h letter password] (str/split line #":?\s|-")]
    [(Integer. l) (Integer. h) letter password]))

(defn valid-password?
  [[l h letter password]]
  (as-> (re-seq (re-pattern letter) password) $
        (count $)
        (and (>= $ l)
             (<= $ h))))



(defn parse-input
  [file]
  (as-> (slurp file) $
        (str/split $ #"\n")
        (map parse-line $)))

(defn count-valid-paswords
  []
  (->> (parse-input "input")
       (filter valid-password?)
       (count)))
