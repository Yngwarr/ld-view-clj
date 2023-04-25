(ns viewer.backlog.core
  (:require-macros [secretary.core :refer [defroute]])
  (:require [viewer.browser.core]
            [goog.events :as events]
            [reagent.dom]
            [re-frame.core :as rf]
            [secretary.core :as secretary]
            [viewer.backlog.db]
            [viewer.backlog.views]
            [viewer.backlog.subs]
            [viewer.backlog.events])
  (:import [goog History]
           [goog.history EventType]))

(rf/dispatch-sync [:init-db])

(defroute "/" [] (rf/dispatch [:clear-list]))
(defroute "/:list-name" [list-name]
  (rf/dispatch [:show-list (keyword list-name)]))

(defn render []
  (reagent.dom/render
    [viewer.backlog.views/app]
    (.getElementById js/document "app")))

;; -------- COMMON --------

(defonce ^:export history
  (doto (History.)
    (events/listen
     EventType.NAVIGATE
     (fn [^js/goog.History.Event event]
       (println "navigated")
       (secretary/dispatch! (.-token event))))
    (.setEnabled true)))

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
