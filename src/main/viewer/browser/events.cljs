(ns viewer.browser.events
  (:require
   [viewer.browser.db :refer [default-db]]
   [clojure.string :as str]
   [ajax.core :as ajax]
   [day8.re-frame.http-fx]
   [re-frame.core :refer [reg-event-db reg-event-fx]]))

(defn nodes-link [ids]
  (str "https://api.ldjam.com/vx/node2/get/" (str/join "+" ids)))

(reg-event-db
 :init-db
 (fn [_db _event]
   default-db))

(reg-event-fx
 :crawl-user
 (fn [{db :db :as _cofx} [_ user-slug]]
   {:db (assoc db :loading? true)
    :fx [[:http-xhrio
          {:method :get
           :uri (str "https://api.ldjam.com/vx/node2/walk/1/users/" user-slug "?node")
           :response-format (ajax/json-response-format {:keywords? true})
           :on-success [:update-user-info]
           :on-failure [:handle-user-error]}]]}))

;; TODO test if (= "user" (:type node))
(reg-event-fx
 :update-user-info
 (fn [{db :db :as _cofx} [_ response]]
   (let [node (-> response :node first)
         slug (:slug node)
         name (:name node)
         id (:id node)]
     {:db (assoc db :user (assoc (:user db) :id id :slug slug :name name)
                 :loading? false)
      :fx [[:dispatch [:crawl-game-list id]]]})))

(reg-event-fx
 :crawl-game-list
 (fn [{db :db :as _cofx} [_ user-id]]
   {:db (assoc db :loading? true)
    :fx [[:http-xhrio
          {:method :get
           :uri (str "https://api.ldjam.com/vx/node/feed/" user-id "/authors/item/game?limit=250")
           :response-format (ajax/json-response-format {:keywords? true})
           :on-success [:crawl-games]
           :on-failure [:handle-game-list-error]}]]}))

;; TODO handle the case when (not= 200 (:status response))
(reg-event-fx
 :crawl-games
 (fn [{db :db :as _cofx} [_ response]]
   (let [ids (->> response :feed (mapv :id))]
     {:db (assoc db :loading? true)
      :fx [[:http-xhrio
            {:method :get
             :uri (nodes-link ids)
             :response-format (ajax/json-response-format {:keywords? true})
             :on-success [:update-games]
             :on-failure [:handle-games-error]}]]})))

(defn parse-category [kw]
  (let [names {"01" :overall
               "02" :fun
               "03" :innovation
               "04" :theme
               "05" :graphics
               "06" :audio
               "07" :humor
               "08" :mood}
        s (name kw)]
    (reduce (fn [_acc [num word]]
              (when (re-find (re-pattern num) s)
                (reduced word))) nil names)))

(defn apply-to-keys [m f]
  (reduce (fn [acc [k v]] (assoc acc (f k) v)) {} m))

(defn parse-magic [magic]
  (reduce (fn [acc [k v]]
            (let [s (name k)
                  category (parse-category k)]
              (cond
                (re-find #"average" s)
                (assoc-in acc [:average category] v)

                (re-find #"result" s)
                (assoc-in acc [:result category] v)

                :else acc)))
          {:average {} :result {}} magic))

(reg-event-db
 :update-games
 (fn [db [_ response]]
   (let [games (mapv #(select-keys
                       (merge
                        (assoc % :ratings (apply-to-keys (:grade %) parse-category))
                        (parse-magic (:magic %)))
                       [:slug :path :name :comments :ratings :average :result]) (:node response))]
     (assoc db :games games))))

;; TODO handle errors
;; (reg-event-fx :handle-user-error)
;; (reg-event-fx :handle-game-list-error)
;; (reg-event-fx :handle-games-error)
