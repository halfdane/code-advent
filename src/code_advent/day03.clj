(ns code-advent.day03
  (:gen-class)
  (:use clojure.test))

(defn move [dir]
  (case dir
    \> '(+1 0)
    \< '(-1 0)
    \^ '(0 +1) 
    \v '(0 -1)
    '(0 0)))

(defn moves [dirs]
  (map move dirs))

(defn positions-of [dirs]
  (reductions (partial map +) (moves dirs)))

(defn positions-from-home-of [dirs]
  (conj (positions-of dirs) '(0 0)))

(defn positions [dirs]
  (seq (set (positions-from-home-of dirs))))

(defn robositions [dirs]
  (seq (set 
        (concat 
         (positions-from-home-of (take-nth 2 dirs)) 
         (positions-from-home-of (take-nth 2 (rest dirs)))))))

(deftest examples
  (is (== 2 (count (positions ">"))))
  (is (== 4 (count (positions "^>v<"))))
  (is (== 2 (count (positions "^v^v^v^v^v")))))

(deftest robo-santa-examples
  (is (== 3 (count (robositions "^v"))))
  (is (== 3 (count (robositions "^>v<"))))
  (is (== 11 (count (robositions "^v^v^v^v^v")))))

(deftest should-collect-moves
  (is (= '((0 1) (0 1) (1 0) (0 -1)) (moves "^^>v"))))

(deftest should-add-up-moves
  (is (= '((0 1) (0 2) (1 2) (1 1)) (positions-of "^^>v"))))

(deftest should-include-home
  (is (= '((0 0) (0 1) (0 2) (1 2) (1 1)) 
         (positions-from-home-of "^^>v"))))

(deftest should-have-unique-positions
  (is (= '((0 0) (1 0)) (unique-positions "><><><><"))))

(defn solve []
  (println 
   "Unique positions by single santa: " 
   (count (positions (slurp "resources/day03.txt"))))
  (println 
   "Unique position by santa and robo-santa: "(count (robositions (slurp "resources/day03.txt"))))
)
