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

(deftest should-find-three-vowels
  (is (not (= true (vowels? 3 "agi"))))
  (is (= true (vowels? 3 "aei")))
  (is (= true (vowels? 3 "xazegov")))
  (is (= true (vowels? 3 "aeiouaeiouaeiou"))))

(deftest should-find-repeats
  (is (= true (repeats? "xx")))
  (is (= true (repeats? "abcdde")))
  (is (= true (repeats? "aabbccdd"))))

(defn first-rules [input]
  (if (and 
       (vowels? 3 input)
       (repeats? input)
       (not (forbidden-strings? input)))
    :nice
    :naughty))

(deftest should-find-naughties-by-first-rules
  (is (= :naughty (first-rules "jchzalrnumimnmhp")))
  (is (= :naughty (first-rules "haegwjzuvuyypxyu")))
  (is (= :naughty (first-rules "dvszwmarrgswjxmb"))))

(deftest should-find-nicies-by-first-rules
  (is (= :nice (first-rules "ugknbfddgicrmopn")))
  (is (= :nice (first-rules "aaa"))))

(defn repeating-pair? [input]
  (re-find #"([a-z][a-z]).*\1" input))

(deftest should-find-repeating-pairs
  (is (= true (boolean (repeating-pair? "xyxy"))))
  (is (= false (boolean (repeating-pair? "aaa")))))

(defn repeats-char-with-one-between? [input]
  (re-find #"(.).\1" input))

(deftest should-find-repeating-char-with-one-between
  (is (= true (boolean (repeats-char-with-one-between? "xyx"))))
  (is (= true (boolean (repeats-char-with-one-between? "abcdefeghi"))))
  (is (= true (boolean (repeats-char-with-one-between? "aaa"))))
)

(defn second-rules [input]
  (if (and
       (repeating-pair? input)
       (repeats-char-with-one-between? input))
    :nice
    :naughty))

(deftest should-find-naughties-by-second-rules
  (is (= :naughty (second-rules "uurcxstgmygtbstg")))
  (is (= :naughty (second-rules "ieodomkazucvgmuy"))))

(deftest should-find-nicies-by-second-rules
  (is (= :nice (second-rules "qjhvhtzxzqqjkmpb")))
  (is (= :nice (second-rules "xxyxx"))))

(defn nice? [rule input]
  (= :nice (rule input)))

(defn solve []
  (let [filter-1 (partial nice? first-rules)
        filter-2 (partial nice? second-rules)
        data (split-lines (slurp "resources/day05.txt"))]
    [(count (filter filter-1 data))
     (count (filter filter-2 data))]))

