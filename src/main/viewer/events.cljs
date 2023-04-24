(ns viewer.events
  (:require [viewer.db :refer [default-db]]
            [re-frame.core :refer [reg-event-fx reg-event-db]]))

;; TODO load username from localStorage
;; TODO load selected list from url params
(reg-event-fx
  :init-db
  (fn [_ _] {:db default-db}))

(reg-event-db
 :clear-list
 (fn [db]
   (assoc db :selected-list nil)))

(reg-event-db
 :show-list
 (fn [db [_ list-name]]
   (assoc db :selected-list list-name)))
