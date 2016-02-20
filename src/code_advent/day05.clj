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

(defn char-pairs [input]
  (partition 2 1 input))

(deftest should-get-char-pairs
  (is (= '((\x \y) (\y \x) (\x \y)) 
         (char-pairs "xyxy")))
  (is (= '((\a \a) (\a \b) (\b \c) (\c \d) (\d \e) (\e \f) (\f \g) (\g \a) (\a \a)) 
         (char-pairs "aabcdefgaa"))))

(deftest should-filter-same-chars-with-others-between)

(defn second-rules [input]
  (if 
      (vowels? 3 input)
    :nice
    :naughty))

(defn nice? [rule input]
  (= :nice (rule input)))

(defn solve []
  (let [filter-1 (partial nice? first-rules)
        filter-2 (partial nice? second-rules)
        data (split-lines (slurp "resources/day05.txt"))]
    [(count (filter filter-1 data))
     (count (filter filter-2 data))]))

