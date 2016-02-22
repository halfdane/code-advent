(ns code-advent.day06
  (:gen-class)
  (:use clojure.test)
  (:use [clojure.string :only (split split-lines)]))

(defn positions [[x1 y1] [x2 y2]]
  (for [y (range y1 (+ y2 1)) 
        x (range x1 (+ x2 1))] (list x y)))

(defn turn-on [p1 p2 existing]
  (concat existing (positions p1 p2)))

(defn turn-off [p1 p2 existing]
  (remove (set (positions p1 p2)) existing))

(defn toggle [p1 p2 existing]
  (let [toggle-these (positions p1 p2)
        to-be-turned-off (filter (set toggle-these) existing)
        to-be-turned-on (remove (set existing) toggle-these)]
    (remove (set to-be-turned-off) 
            (set  (concat existing to-be-turned-on)))))

(defn action [verb]
  (case verb 
    "turn on" turn-on
    "turn off" turn-off
    "toggle" toggle))

(defn set-lighting [existing str]
  (let [matches (re-find #"([^\d]*) (\d*),(\d*) through (\d*),(\d*)" str)
        [verb & positions] (rest matches)
        [x1 y1 x2 y2] (map read-string positions)]
    ((action verb) (list x1 y1) (list x2 y2) existing)))

(defn set-lighting-multi [list-of-str]
  (reduce set-lighting '() list-of-str))

(defn count-lights [list-of-str]
  (count (set-lighting-multi list-of-str)))

(deftest should-include-lights
  (is (= '((1 0) (2 0) (1 1) (2 1)) (turn-on '(1 0) '(2 1) '())))
  (is (= '((3 3) (1 0) (2 0) (1 1) (2 1)) (turn-on '(1 0) '(2 1) '((3 3))))))

(deftest should-remove-lights
  (is (= '((1 0) (1 1)) (turn-off '(2 0) '(2 1) '((1 0) (2 0) (1 1) (2 1)))))
  (is (= '((1 0) (1 1)) (turn-off '(2 0) '(2 1) (turn-on '(1 0) '(2 1) '())))))

(deftest should-toggle-lights
  (is (= '((2 0) (2 1)) (toggle '(1 0) '(1 1) (turn-on '(1 0) '(2 1) '()))))
  (is (= '((1 0) (2 0) (2 1) (1 2)) (toggle '(1 1) '(1 2) (turn-on '(1 0) '(2 1) '())))))

(deftest should-interpret-single-input-line
  (is (= (turn-on '(1 0) '(2 1) '()) 
         (set-lighting '()  "turn on 1,0 through 2,1"))))

(deftest should-interpret-multiple-input-lines
  (is (= '((2 0) (2 1)) 
         (set-lighting-multi
          '("turn on 1,0 through 2,1"
           "toggle 1,0 through 1,1")))))

(deftest examples
  (is (= (* 1000 1000) (count-lights (list "turn on 0,0 through 999,999"))))
  (is (= (- (* 1000 1000) 4) (count-lights (list "turn on 0,0 through 999,999" "turn off 499,499 through 500,500"))))
  (is (= (- (- (* 1000 1000) 4) 1000) (count-lights (list "turn on 0,0 through 999,999" "turn off 499,499 through 500,500" "toggle 0,0 through 999,0"))))
)

(defn solve []
  (count-lights (split-lines (slurp "resources/day06.txt"))))
