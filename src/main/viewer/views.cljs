(ns viewer.views
  (:require [re-frame.core :refer [subscribe dispatch]]
            [viewer.link :as link]))

;; TODO add a link to the game
(defn entry [author game]
  [:li {:key author}
   (str (name author) " made ") [:a {:href (link/backlog)} game]])

(defn entry-list []
  (let [entries @(subscribe [:current-list])]
    (if (nil? entries)
      [:p "Invalid list name, try again."]
      [:ul (map (fn [[author game]] (entry author game)) entries)])))

(defn list-link [list-id]
  (let [list-name (name list-id)]
    [:li {:key list-id} [:a {:href (link/backlog list-name)} list-name]]))

(defn list-selection [list-names]
  [:ul (map list-link list-names)])

(defn backlog []
  (if @(subscribe [:selected-list])
    (entry-list)
    (list-selection (keys @(subscribe [:lists])))))
