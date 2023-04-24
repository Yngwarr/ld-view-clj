(ns viewer.browser
  (:require [reagent.dom]
            [re-frame.core :as rf]
            [viewer.views]
            [viewer.subs]
            [viewer.events]))

(rf/dispatch-sync [:init-db])

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
