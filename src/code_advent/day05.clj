(ns code-advent.day05
  (:gen-class)
  (:use clojure.test)
  (:use [clojure.string :only (split split-lines)]))

(defn vowels [input]
  (filter #(some #{%} '(\a \e \i \o \u)) input))

(defn vowels? [expected-count input]
  (<= expected-count (count (vowels input))))

(defn all-repetitions [input]
  (filter #(<= 2 (count %)) (partition-by identity input)))

(defn repeats? [input]
  (not (empty? (all-repetitions input))))

(defn substring? [sub st]
  (not= (.indexOf st sub) -1))

(defn forbidden-strings? [input]
  (or 
   (substring? "ab" input)
   (substring? "cd" input)
   (substring? "pq" input)
   (substring? "xy" input)))

(defn typeof [input]
  (if (and 
       (vowels? 3 input)
       (repeats? input)
       (not (forbidden-strings? input)))
    :nice
    :naughty))

(defn nice? [input]
  (= :nice (typeof input)))

(deftest should-find-three-vowels
  (is (not (= true (vowels? 3 "agi"))))
  (is (= true (vowels? 3 "aei")))
  (is (= true (vowels? 3 "xazegov")))
  (is (= true (vowels? 3 "aeiouaeiouaeiou"))))

(deftest should-find-repeats
  (is (= true (repeats? "xx")))
  (is (= true (repeats? "abcdde")))
  (is (= true (repeats? "aabbccdd"))))

(deftest should-find-naughties
  (is (= :naughty (typeof "jchzalrnumimnmhp")))
  (is (= :naughty (typeof "haegwjzuvuyypxyu")))
  (is (= :naughty (typeof "dvszwmarrgswjxmb"))))

(deftest should-find-nicies
  (is (= :nice (typeof "ugknbfddgicrmopn")))
  (is (= :nice (typeof "aaa"))))

(defn solve []
  (count (filter nice? (split-lines (slurp "resources/day05.txt")))))
