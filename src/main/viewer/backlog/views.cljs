(ns viewer.backlog.views
  (:require [re-frame.core :refer [subscribe]]))

(defn link
  ([] "#/")
  ([list-name] (str "#/" list-name)))

;; TODO add a link to the game
(defn entry [author]
  [:li {:key author}
   (str author " made ") [:a {:href (link)} "The placeholder game"]])

(defn entry-list []
  (let [entries @(subscribe [:current-list])]
    (if (nil? entries)
      [:p "Invalid list name, try again."]
      [:ul (map (fn [author] (entry author)) entries)])))

(defn list-link [list-id]
  (let [list-name (name list-id)]
    [:li {:key list-id} [:a {:href (link list-name)} list-name]]))

(defn list-selection [list-names]
  [:ul (map list-link list-names)])

(defn app []
  [:<>
   (if @(subscribe [:selected-list])
     (entry-list)
     (list-selection (keys @(subscribe [:author-lists]))))
   (when @(subscribe [:loading?])
     [:p "Now loading..."])])
