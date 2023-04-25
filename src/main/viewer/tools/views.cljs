(ns viewer.tools.views
  (:require [re-frame.core :refer [subscribe]]))

(defn app []
  [:<>
   (let [ids @(subscribe [:shown-ids])]
     (if (nil? ids)
       [:p "Wait for it..."]
       [:p (str "Ids: " ids)]))
   (when @(subscribe [:loading?])
     [:p "Now loading..."])])
