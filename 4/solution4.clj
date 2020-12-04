(ns solution4.core
  (:require [clojure.string :as str]))

(def required-fields
  '(:byr :iyr :eyr :hgt :hcl :ecl :pid))

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

(defn valid-height?
  [height-and-unit]
  (let [[h unit] (re-seq #"\d+|cm|in" height-and-unit)
        height        (Integer. h)]
    (condp = unit
      "in"  (and (>= height 59)  (<= height 76))
      "cm"  (and (>= height 150) (<= height 193))
      false)))

(defn valid-year?
  [type year low high]
  (and (>= year low) (<= year high)))

(defn valid-passport?
  [passport]
  (reduce (fn [acc curr] (and acc (some #{curr} (keys passport)))) true required-fields))

(defn valid-passport2?
  [field]
  (and
   (when (:byr field) (valid-year? :byr (Integer. (:byr field)) 1920 2002))
   (when (:iyr field) (valid-year? :iyr (Integer. (:iyr field)) 2010 2020))
   (when (:eyr field) (valid-year? :eyr (Integer. (:eyr field)) 2020 2030))
   (when (:hgt field) (valid-height? (:hgt field)))
   (when (:hcl field) (re-matches #"#[a-f0-9]{6}" (:hcl field)))
   (when (:ecl field) (re-matches #"(amb|blu|brn|gry|grn|hzl|oth)" (:ecl field)))
   (when (:pid field) (re-matches #"\d{9}" (:pid field)))))

(defn check-valid-passports
  []
  (->> (parse-input "input")
       (filter valid-passport?)
       (count)))

(defn check-valid-passports2
  []
  (->> (parse-input "input")
       (filter valid-passport2?)
       (count)))
