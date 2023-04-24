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
  :lists
  (fn [db _] (:lists db)))

; -------- COMPOSITE --------

(reg-sub
 :current-list
 (fn [] [(subscribe [:selected-list]) (subscribe [:lists])])
 (fn [[selected lists]] (get lists selected)))
