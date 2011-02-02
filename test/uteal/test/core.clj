(ns uteal.test.core
  (:use [uteal core test] :reload))

;; (describe undef
;;   (it (= )))

;; (fact (not (resolve? foo))
;;       (do (def foo 1)
;;           (resolve? foo)))

;; (fact (do (def foo 1) (undef foo) (when (ns-resolve *ns* foo)))

(fact (by 2 + [1 2 3 4]) 7)

(let [a 1 b nil c 0] 
  (describe nil-safe
    (it "can take a single var"
      (= (nil-safe a (+ a 1)) 2))
    (it "can take a vector"
      (= (nil-safe [a c] (+ a c 1)) 2))
    (it "only evals body when var(s) aren't nil"
      (nil? (nil-safe b (+ b 1))))))

(describe guard
  (it "returns `else` when `then` doesn't pass `pred`"
    (= (guard nil? 2 :not-2)
       :not-2))
  (it "returns `then` when `then` does pass `pred`"
    (= (guard even? 2 :not-even)
       2)))

(fact (guard even? 2 :not-even) 2)

(let [a 1]
  (fact (when-hash a) (when a {:a a})))

(let [a 1, b 2]
  (fact (as-hash a b) {:a a :b b}))