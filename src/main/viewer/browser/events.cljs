(ns viewer.browser.events
  (:require
   [viewer.browser.db :refer [default-db]]
   [re-frame.core :refer [reg-event-db reg-event-fx]]))

(reg-event-db
 :init-db
 (fn [db _event]
   default-db))

(reg-event-fx
 :crawl-user
 (fn [{db :db :as _cofx} [_ user-slug]]
   {:db (assoc db :user-slug user-slug)}))
