(ns viewer.tools.core
  (:require-macros [secretary.core :refer [defroute]])
  (:require [viewer.browser.core]
            [goog.events :as events]
            [reagent.dom]
            [re-frame.core :as rf]
            [secretary.core :as secretary]
            [viewer.tools.db]
            [viewer.tools.views]
            [viewer.tools.subs]
            [viewer.tools.events])
  (:import [goog History]
           [goog.history EventType]))

(rf/dispatch-sync [:init-db])

(defroute "/get-ids/:names" [names]
  (rf/dispatch [:get-ids names]))

(defn render []
  (reagent.dom/render
    [viewer.tools.views/app]
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
  (js/console.log "start tools")
  (rf/clear-subscription-cache!)
  (render))

(defn ^:export init []
  ;; init is called ONCE when the page loads
  ;; this is called in the index.html and must be exported
  ;; so it is available even in :advanced release builds
  (js/console.log "init tools")
  (start!))

;; this is called before any code is reloaded
(defn ^:dev/before-load stop []
  (js/console.log "stop tools"))
