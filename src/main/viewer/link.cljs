(ns viewer.link)

(defn backlog
  ([] "#/backlog")
  ([list-name] (str "#/backlog/" list-name)))
