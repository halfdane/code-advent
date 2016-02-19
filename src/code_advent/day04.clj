(ns code-advent.day04
  (:gen-class)
  (:use clojure.test)
  (:use [digest :only [md5]] ))

(defn is-match? [key input]
  (= "00000" (subs (md5 (str key input)) 0 5)))

(defn mine [input]
  (let [matches (partial is-match? input)]
    (first (filter matches (range)))))

(deftest should-find-match
  (is (is-match? "abcdef" 609043))
  (is (is-match? "pqrstuv" 1048970))
  (is (not (is-match? "foo" 72891)))
)

(deftest should-calculate-md5
  (is (= "acbd18db4cc2f85cedef654fccc4a4d8" (md5 "foo"))))


(deftest should-mine-examples
  (is (== 609043 (mine "abcdef")))
  (is (== 1048970 (mine "pqrstuv"))))

(defn solve []
  (println "key iwrupvqb and " (mine "iwrupvqb")))
