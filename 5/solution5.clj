(ns solution5.core
  (:require [clojure.string :as str]))

(defn parse-input
  [input]
  (as-> (slurp input) $
        (str/split $ #"\n")
        (map seq $)))

(defn search-seat
  [low-or-high seats]
  (let [half-seats (Math/round (double (/ (count seats) 2)))]
    (condp = low-or-high
      \B (drop half-seats seats)
      \F (take half-seats seats)
      \R (drop half-seats seats)
      \L (take half-seats seats))))

(defn get-position-from-bsp
  [bsp row-or-column]
  (loop [[lh seats] (condp = row-or-column
                      :row    [(take 7 bsp) (range 0 128)]
                      :column [(drop 7 bsp) (range 0 8)])]
    (if (= (count lh) 0)
      (first seats)
      (recur [(rest lh) (search-seat (first lh) seats)]))))

(defn get-seat-id-from-bsp
  [bsp]
  (let [row    (get-position-from-bsp bsp :row)
        column (get-position-from-bsp bsp :column)]
    (+ column (* row 8))))

(defn find-highest-seat-id-from-bsps
  []
  (->> (parse-input "input")
       (map (fn [bsp] (get-seat-id-from-bsp bsp)))
       (reduce (fn [acc curr] (if (> curr acc) curr acc)) 0)))

(defn find-my-seat-id
  []
  (let [ids      (->> (parse-input "input")
                      (map (fn [bsp] (get-seat-id-from-bsp bsp)))
                      (sort))
        cons-ids (range (first ids) (inc (last  ids)))]
    (- (reduce - ids) (reduce - cons-ids))))

