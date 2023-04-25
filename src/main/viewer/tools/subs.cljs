(ns viewer.tools.subs
  (:require [re-frame.core :refer [reg-sub subscribe]]))

; -------- ATOMIC --------

(reg-sub
  :loading?
  (fn [db _] (:loading? db)))

(reg-sub
  :shown-ids
  (fn [db _] (:shown-ids db)))

; -------- COMPOSITE --------
