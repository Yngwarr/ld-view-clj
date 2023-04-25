(ns viewer.backlog.events
  (:require
    [viewer.backlog.db :refer [default-db]]
    [ajax.core :as ajax]
    [day8.re-frame.http-fx]
    [re-frame.core :refer [reg-event-db reg-event-fx]]))

;; TODO load username from localStorage
;; TODO load selected list from url params
(reg-event-fx
  :init-db
  (fn [_ _] {:db default-db}))

(reg-event-db
  :clear-list
  (fn [db]
    (assoc db :selected-list nil)))

;; TODO send the request
;(reg-event-fx
  ;:show-list
  ;(fn [{:keys [db]}]
    ;{:db (assoc db :loading? true)
    ;:fx [[:http-xhrio {:method :get}]]}))

(reg-event-db
  :show-list
  (fn [db [_ list-name]]
    (assoc db :selected-list list-name)))
