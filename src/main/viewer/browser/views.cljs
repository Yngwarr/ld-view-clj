(ns viewer.browser.views
  (:require [clojure.string :as str]
            [re-frame.core :refer [subscribe]]
            [viewer.browser.svg :refer [plot]]))

(defn app []
  (if-let [user-name @(subscribe [:user :name])]
    [:<>
     [:h1 (str "Hello, " user-name "!")]
     [:p (str "@" @(subscribe [:user :slug]))]
     [:p (str "id: " @(subscribe [:user :id]))]]
    [:<>
     [:h1 "Provide user-name."]
     ;; (js/console.log (- 10 2 3))
     (plot [[50 3.2] [51 4.0] [53 3.9]] [500 500])]))
