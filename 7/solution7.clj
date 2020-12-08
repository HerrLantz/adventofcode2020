(ns solution7.core
  (:require [clojure.string :as str]
            [clojure.set    :as set]))

(defn extract-colors-and-numbers
  [s]
  (re-seq #"(?!bags\b)\b[a-z]+\s(?!no|other|bags\b)[a-z]+|\d+" s))


(defn string-color->key-color
  [s]
  (keyword (str/replace s #"\s" "-")))

(defn list->color-and-amount
  [l]
  (loop [[n c] (take 2 l)
         acc   {}
         left  (drop 2 l)]
    (if (nil? n)
      acc
      (recur (take 2 left)
             (conj acc {(string-color->key-color c)
                        (Integer. n)})
             (drop 2 left)))))

(defn color-list->color-map
  [l]
  (reduce (fn [acc curr]
            (let [key-color   (string-color->key-color (first curr))
                  list-colors (list->color-and-amount  (rest curr))]
              (conj acc {key-color list-colors}))) {} l))

(defn parse-input
  [input]
  (as-> (slurp input) $
        (str/split $ #"\n")
        (map extract-colors-and-numbers $)
        (color-list->color-map $)
        (into {} $)))

(defn can-hold-bag?
  ([all-bags]
   (can-hold-bag? #{} all-bags))
  ([visited all-bags]
   (memoize (fn [current-bag wanted-bag]
              (let [v         (conj visited current-bag)
                    nb        (set (keys (get all-bags current-bag)))
                    next-bags (clojure.set/difference nb v)]
                (or (some? (get-in all-bags [current-bag wanted-bag]))
                    (some identity
                          (map (fn [cb]
                                 ((can-hold-bag? v all-bags) cb wanted-bag))
                               next-bags))))))))

(defn count-bags
  []
  (let [bags (parse-input "input")]
    (count (filter identity (map (fn [current-bag] ((can-hold-bag? bags) (first current-bag) :shiny-gold)) bags)))))

