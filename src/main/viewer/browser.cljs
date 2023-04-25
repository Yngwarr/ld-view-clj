(ns viewer.browser
  (:require-macros [secretary.core :refer [defroute]])
  (:require [goog.events :as events]
            [reagent.dom]
            [re-frame.core :as rf]
            [secretary.core :as secretary]
            [viewer.views]
            [viewer.subs]
            [viewer.events])
  (:import [goog History]
           [goog.history EventType]))

(rf/dispatch-sync [:init-db])

;; backlog endpoints
(defroute "/backlog" [] (rf/dispatch [:clear-list]))
(defroute "/backlog/:list-name" [list-name]
  (rf/dispatch [:show-list (keyword list-name)]))

;; utils
(defroute "/get-ids/:names" [names]
  (rf/dispatch [:get-ids names]))

(defonce history
  (doto (History.)
    (events/listen
     EventType.NAVIGATE
     (fn [^js/goog.History.Event event]
       (secretary/dispatch! (.-token event))))
    (.setEnabled true)))

(defn render []
  (reagent.dom/render
    [viewer.views/backlog]
    (.getElementById js/document "app")))

;; start is called by init and after code reloading finishes
(defn ^:dev/after-load start! []
  (js/console.log "start")
  (rf/clear-subscription-cache!)
  (render))

(defn ^:export init []
  ;; init is called ONCE when the page loads
  ;; this is called in the index.html and must be exported
  ;; so it is available even in :advanced release builds
  (js/console.log "init")
  (start!))

;; this is called before any code is reloaded
(defn ^:dev/before-load stop []
  (js/console.log "stop"))
