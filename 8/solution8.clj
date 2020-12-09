(ns solution8.core
  (:require [clojure.string :as str]))

(defn parse-input
  [input]
  (as-> (slurp input) $
        (str/split $ #"\n")
        (mapv #(let [[op value] (str/split % #"\s")]
                 [(keyword op) (Integer. value)]) $)))

(defn run-program
  [program]
  (loop [visited #{}
         acc     0
         pc      0
         states  []]
    (if (>= (inc pc) (count program))
      (println "YAY!!")
      nil)
    (if (or (some #{pc} visited) (>= (inc pc) (count program)))
      states
      (let [[op value]       (nth program pc)
            [new-acc new-pc] (condp = op
                               :nop [acc           (inc pc)]
                               :acc [(+ acc value) (inc pc)]
                               :jmp [acc           (+ value pc)])]
        (recur (conj visited pc)
               new-acc
               new-pc
               (conj states {:acc                acc
                             :op                 op
                             :pc                 pc
                             :normal-termination (>= (inc new-pc) (count program))}))))))

(defn get-acc-from-one-iteration
  []
  (-> (parse-input "input")
      (run-program)
      (last)
      (:acc)))

(defn get-acc-from-normal-termination
  []
  (let [program (parse-input "input")]
    (loop [pc-jmps (->> (run-program program)
                        (filter #(= (:op %) :jmp)))]
      (let [pc              (:pc (first pc-jmps))
            updated-program (assoc-in program [pc 0] :nop)
            state           (run-program updated-program)]
        (if (:normal-termination (last state))
          (:acc (last state))
          (recur (rest pc-jmps)))))))

