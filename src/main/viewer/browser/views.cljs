(ns viewer.browser.views
  (:require [re-frame.core :refer [subscribe]]))

(defn app []
  [:<>
   [:h1 (if-let [user-slug @(subscribe [:user-slug])]
          (str "Hello, " user-slug "!")
          "Provide user-name.")]])
