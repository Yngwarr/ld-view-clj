(ns viewer.link)

(defn backlog
  ([] "#")
  ([list-name] (str "#/" list-name)))
