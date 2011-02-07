(ns uteal.core)

(defmacro undef
  "Undefine a symbol, normally because you moved a function to another namespace.
Ex. (undef function-name)"
  [sym]
  `(ns-unmap (symbol (str *ns*)) '~sym))

(defmacro by [x fname forms]
  `(do ~@(map (partial cons fname) (partition x forms))))
; (let [c [a 1 b 2]] (by 2 def c)) can't work bc c isn't known at compile time

(defmacro defs [& args] `(by 2 def ~args))
(defmacro defns [& args] `(by 3 defn ~args))

(defmacro nil-safe
  "only eval code in body if `vars` (vector of syms or just sym] aren't nil
ex. (let [a nil] (nil-safe a (.length a))) => nil instead of NPE cause (.length a) is never executed."
  [vars & body]
  `(when-not (or (and (vector? ~vars) (some nil? ~vars)) (nil? ~vars))
     ~@body))

;;; ssyntax


;;; from twoguysarguing
(defmacro guard
  "tests `then` with `pred` and returns `then` or `else`"
  [pred then else]
  `(let [x# ~then]
     (if (~pred x#)
       x#
       ~else)))

;;; not working, catch doesn't seem to work
;; (defmacro resolve? [sym]
;;   `(try (var? (var ~sym))
;;         (catch Exception ~'e
;;           false)))

(defmacro when-hash
  "Ex. (when-hash a) => (when a {:a a})"
  [x]
  `(when ~x {~(keyword x) ~x}))

(defmacro as-hash
  "Ex. (as-hash a b) => {:a a :b b}"
  [& xs]
  (apply hash-map
         (interleave (map keyword xs)
                     xs)))

(defmacro throw-exception [s]
  `(throw (Exception. ~s)))

(defmacro wtf [s]
  `(throw-exception ~s))

;;; from fail with me blog
(defmacro dlet [bindings & body]
  `(let [~@(mapcat (fn [[n v]]
                     (if (or (vector? n) (map? n))
                       [n v]
                       [n v '_ `(println (name '~n) " : " ~n)]))
                   (partition 2 bindings))]
     ~@body))

;;; from fogus evalive
(defmacro lexical-context
  "ex. use in let as (print (lexical-context)) to see map of locals => values"
  []
  (let [symbols (keys &env)]
    (zipmap (map (fn [sym] `(quote ~sym))
                 symbols)
            symbols)))

