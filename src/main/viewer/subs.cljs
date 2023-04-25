(ns viewer.subs
  (:require [re-frame.core :refer [reg-sub subscribe]]))

; -------- ATOMIC --------

(reg-sub
 :loading?
 (fn [db _] (:loading? db)))

(reg-sub
 :user
 (fn [db _] (:user db)))

(reg-sub
 :selected-list
 (fn [db _] (:selected-list db)))

(reg-sub
 :author-lists
 (fn [db _] (:author-lists db)))

(reg-sub
 :shown-ids
 (fn [db _] (:shown-ids db)))

; -------- COMPOSITE --------

(reg-sub
 :current-list
 (fn [] [(subscribe [:selected-list]) (subscribe [:author-lists])])
 (fn [[selected lists]]
   (get lists selected)))
