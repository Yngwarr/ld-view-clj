(ns viewer.views
  (:require [re-frame.core :refer [subscribe dispatch]]))

(defn entry-list [entries])

(defn list-link [list-id]
  [:li {:key list-id} (name list-id)])

(defn list-selection [list-names]
  [:ul (map list-link list-names)])

(defn backlog []
  (let [selected-list @(subscribe [:selected-list])
        lists @(subscribe [:lists])]
    (if selected-list
      (entry-list (get lists selected-list))
      (list-selection (keys lists)))))
