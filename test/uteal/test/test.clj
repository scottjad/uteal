(ns uteal.test.test
  (:use uteal.test
        clojure.test))

(describe it
  (it "tests if test fails" (= 1 1))
  (it (not= false true))
  (it (not= 0 1))
  (it "foo" (not= 0 1))
  (it "can take multiple tests" (= 1 1) (= 2 2)))

(describe fact
  (it (= nil ((fact true true))))
  (it (= nil ((fact false false)))))

