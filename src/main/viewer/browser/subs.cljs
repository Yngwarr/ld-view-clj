(ns viewer.browser.subs
  (:require [re-frame.core :refer [reg-sub subscribe]]))

; -------- ATOMIC --------

(reg-sub
 :loading?
 (fn [db _query]
   (:loading? db)))

(reg-sub
 :user-map
 (fn [db _query]
   (:user db)))

; -------- COMPOSITE --------

(reg-sub
 :user
 (fn [] (subscribe [:user-map]))
 (fn [user [_ prop]]
   (prop user)))
