(ns code-advent.day04
  (:gen-class)
  (:use clojure.test)
  (:use [digest :only [md5]] ))

(defn zeroes [count]
  (apply str (repeat count "0")))

(defn is-match? [key prefix-length input]
  (= 
   (zeroes prefix-length) 
   (subs (md5 (str key input)) 0 prefix-length)))

(defn mine [input prefix-length]
  (let [matches (partial is-match? input prefix-length)]
    (first (filter matches (range)))))

(deftest should-create-list-of-zeroes
  (is (= "00" (zeroes 2)))
  (is (= "00000" (zeroes 5)))
  (is (= "000000" (zeroes 6)))
)

(deftest should-find-match
  (is (is-match? "abcdef" 5 609043))
  (is (is-match? "pqrstuv" 5 1048970))
  (is (not (is-match? "foo" 5 72891))))

(deftest should-calculate-md5
  (is (= "acbd18db4cc2f85cedef654fccc4a4d8" (md5 "foo"))))


(deftest should-mine-examples
;; takes rather long, so not activated for now
;;  (is (== 609043 (mine "abcdef" 5)))
;;  (is (== 1048970 (mine "pqrstuv" 5)))
)

(defn solve [prefix-length]
  (let [suf (mine "iwrupvqb" prefix-length)
        string (str "iwrupvqb" suf)]
    (println string)
    (println (md5 string))
    (println suf)))
