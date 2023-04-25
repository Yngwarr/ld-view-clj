(ns viewer.subs
  (:require [re-frame.core :refer [reg-sub subscribe]]))

; -------- ATOMIC --------

(reg-sub
 :user
 (fn [db _] (:user db)))

(reg-sub
 :selected-list
 (fn [db _] (:selected-list db)))

(reg-sub
 :author-lists
 (fn [db _] (:author-lists db)))

; -------- COMPOSITE --------

(reg-sub
 :current-list
 (fn [] [(subscribe [:selected-list]) (subscribe [:author-lists])])
 (fn [[selected lists]]
   (get lists selected)))
