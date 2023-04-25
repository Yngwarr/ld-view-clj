(ns viewer.tools.events
  (:require
   [viewer.tools.db :refer [default-db]]
   [ajax.core :as ajax]
   [day8.re-frame.http-fx]
   [re-frame.core :refer [reg-event-db reg-event-fx]]))

(reg-event-fx
 :init-db
 (fn [_ _] {:db default-db}))

(reg-event-fx
 :get-ids
 (fn [{:keys [db]} [_ name-string]]
   {:db (assoc db :loading? true)
    :fx [[:http-xhrio
          {:method :get
           :uri (str "https://api.ldjam.com/vx/node2/walk/1/users/" name-string)
           :response-format (ajax/json-response-format {:keywords? true})
           :on-success [:show-ids]
           :on-failure [:show-error]}]]}))

(reg-event-db
 :show-ids
 (fn [db [_ ids]]
   (assoc db :loading? false :shown-ids (:node_id ids))))
