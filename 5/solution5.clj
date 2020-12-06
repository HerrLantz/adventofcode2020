(ns solution5.core
  (:require [clojure.string :as str]))

(defn parse-input [input]
  (as-> (str/split (slurp input) #"\n") $
        (map (fn [s] (-> (str/replace s #"(L|F)" "0")
                         (str/replace   #"(R|B)" "1"))) $)))

(defn get-seat-ids []
  (sort (map (fn [s] (+ (Integer/parseInt (subs s 7 10) 2)
                        (* (Integer/parseInt (subs s 0 7) 2) 8))) (parse-input "input"))))

(defn get-max-seat-id [] (last (get-seat-ids)))

(defn find-my-seat-id []
  (- (reduce - (get-seat-ids))
     (reduce - (range (first (get-seat-ids)) (inc (last (get-seat-ids)))))))

