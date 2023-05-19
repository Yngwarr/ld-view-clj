(ns viewer.browser.subs
  (:require [re-frame.core :refer [reg-sub subscribe]]))

; -------- ATOMIC --------

(reg-sub
 :loading?
 (fn [db _query]
   (:loading? db)))

(reg-sub
 :user-slug
 (fn [db _query]
   (:user-slug db)))

; -------- COMPOSITE --------
