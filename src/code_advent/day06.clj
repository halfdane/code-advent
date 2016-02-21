(ns code-advent.day06
  (:gen-class)
  (:use clojure.test)
  (:use [clojure.string :only (split split-lines)]))

(defn on [] 1)

(defn off [] 0)

(defn toggle [n]
  (if (= n 0) 1 0))

(defn output [matr n]
  (map #(println (nth matr %)) (range n)))

(defn matrix [n]
  (map #(repeat n %) (repeat n '(0))))

(defn turn-on [matr [x1 y1] [x2 y2]]
  (map (fn [y] (map (fn [x] (println x y)) 
                    (range x1 (+ x2 1)))) (range y1 (+ y2 1))))

(deftest should-turn-on (is (= 1 (on))))

(deftest should-turn-on (is (= 0 (off))))

(deftest should-toggle
  (is (= 0 (toggle 1)))
  (is (= 1 (toggle 0))))

(deftest should-create-matrix-of-3
  (is (= '(((0) (0) (0)) 
           ((0) (0) (0)) 
           ((0) (0) (0))) 
         (matrix 3) )))

(deftest should-turn-on-some-elements
  (is (= '(((0) (0) (0))    ;;(0 2) (1 2) (2 2) 
           ((0) (1) (1))    ;;(0 1) (1 1) (2 1)
           ((0) (1) (1)))   ;;(0 0) (1 0) (2 0)
         (turn-on (matrix 3) '(1 0) '(2 1)) )))


