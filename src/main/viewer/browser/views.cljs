(ns viewer.browser.views
  (:require [re-frame.core :refer [subscribe]]))

(defn app []
  [:<>
   [:h1 (if-let [user-name @(subscribe [:user :name])]
          (str "Hello, " user-name "!")
          "Provide user-name.")]
   [:p (str "@" @(subscribe [:user :slug]))]
   [:p (str "id: " @(subscribe [:user :id]))]])
