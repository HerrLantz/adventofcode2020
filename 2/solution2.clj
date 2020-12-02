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

(defn valid-password2?
  [[l h letter password]]
  (let [cond1 (= letter (str (nth password (dec l))))
        cond2 (= letter (str (nth password (dec h))))]
    (->> [cond1 cond2]
         (filter identity)
         (count)
         (= 1))))

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

(defn count-valid-paswords2
  []
  (->> (parse-input "input")
       (filter valid-password2?)
       count))
