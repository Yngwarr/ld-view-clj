(ns viewer.events
  (:require [viewer.db :refer [default-db]]
            [re-frame.core :refer [reg-event-fx]]))

;; TODO load username from localStorage
;; TODO load selected list from url params
(reg-event-fx
  :init-db
  (fn [_ _] {:db default-db}))
