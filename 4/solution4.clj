(ns solution4.core
  (:require [clojure.string :as str]))

(defn make-key-value
  [kv]
  (let [[k v] (str/split kv #":")]
    {(keyword k) v}))

(defn parse-passport
  [passport]
  (as-> (str/split passport #"\s") $
        (reduce (fn [acc curr] (conj acc (make-key-value curr))) {} $)))

(defn parse-input
  [input]
  (as-> (slurp input) $
        (str/split $ #"\n\n")
        (map parse-passport $)))

(defn valid-passport?
  [passport]
  (and (:byr passport)
       (:iyr passport)
       (:eyr passport)
       (:hgt passport)
       (:hcl passport)
       (:ecl passport)
       (:pid passport)))

(defn check-valid-passports
  []
  (->> (parse-input "input")
       (filter valid-passport?)
       (count)))
