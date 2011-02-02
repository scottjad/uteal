(ns uteal.test
  (:use clojure.test))

(defmacro describe
  "like `deftest` but automatically prepends 'test-'"
  [fname & body]
  `(deftest ~(symbol (str "test-" fname ))
     ~@body))

(defmacro it
  "like `is` but allows the message to be first and can take multiple tests"
  ([test]
     `(it "" ~test))
  ([msg test]
     `(is ~test ~msg))
  ([msg test & tests]
     `(do (it ~msg ~test)
          ~@(for [t# tests]
              `(it ~msg ~t#)))))

(defmacro fact
  "for quick examples, does the `describe`/`it` for you."
  [& exps]
  `(describe ~(symbol (gensym "fact-"))
     (it (= ~@exps))))