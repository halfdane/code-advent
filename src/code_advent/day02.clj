(ns code-advent.day02 
  (:gen-class)
  (:use clojure.test)
  (:use [clojure.string :only (split split-lines)]))

(defn wrap-with-dimensions [[l w h]] 
  (let [a (* l w)
        b (* w h)
        c (* h l)
        additional-paper (min a b c)]
    [(+ (* 2 a) (* 2 b) (* 2 c) additional-paper) 
     0]))

(defn wrap [dimension-string] 
  (wrap-with-dimensions 
   (map bigdec 
        (split dimension-string #"x"))))

(deftest to-int
  (is (== 177 (bigdec "177"))))

(deftest examples
  (is (== 58 (first (wrap "2x3x4"))))
  (is (== 34 (second (wrap "2x3x4"))))
  (is (== 43 (first (wrap "1x1x10"))))
  (is (== 14 (second (wrap "1x1x10"))))
)

(defn day02 []
  (let [data (split-lines (slurp "resources/day02.txt"))
        wrapped (map wrap data)]
    [(reduce + (map first wrapped))
     (reduce + (map second wrapped))]))
