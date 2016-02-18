(ns code-advent.day03
  (:gen-class)
  (:use clojure.test))



(defn move [dir pos]
  (case dir
    ">" (map + [+1 0] pos) 
    "<" (map + [-1 0] pos)
    "^" (map + [0 +1] pos) 
    "v" (map + [0 -1] pos)))

(defn deliver [directions]
  2)

(deftest examples
  (is (== 2 (deliver ">")))
  (is (== 4 (deliver "^>v<")))
  (is (== 2 (deliver "^v^v^v^v^v"))))

(deftest moves
  (is (= [4 8] (move ">" [3 8])))
  (is (= [2 8] (move "<" [3 8])))
  (is (= [3 9] (move "^" [3 8])))
  (is (= [3 7] (move "v" [3 8]))))
