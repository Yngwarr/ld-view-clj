(ns viewer.browser.svg
  (:require [clojure.string :as str]
            [clojure.core.match :refer [match]]))

(defn line [[src-x src-y] [dst-x dst-y] style]
  [:line {:x1 src-x :y1 src-y :x2 dst-x :y2 dst-y :style style}])

(defn polygon [points style]
  [:polygon {:points (str/join " " (mapv #(str/join "," %) points)) :style style}])

(defn triangle [[x y] base height direction style]
  (let [points (match [direction]
                      [:up]
                      [[(- x (/ base 2)) y]
                       [(+ x (/ base 2)) y]
                       [x (- y height)]]

                      [:down]
                      [[(- x (/ base 2)) y]
                       [(+ x (/ base 2)) y]
                       [x (+ y height)]]

                      [:left]
                      [[x (+ y (/ base 2))]
                       [x (- y (/ base 2))]
                       [(- x height) y]]

                      [:right]
                      [[x (+ y (/ base 2))]
                       [x (- y (/ base 2))]
                       [(+ x height) y]])]
    (polygon points style)))

(defn boundary [points]
  (let [bound (reduce (fn [acc [x y]]
                        (cond-> acc
                          (< x (:min-x acc)) (assoc :min-x x)
                          (< y (:min-y acc)) (assoc :min-y y)
                          (> x (:max-x acc)) (assoc :max-x x)
                          (> y (:max-y acc)) (assoc :max-y y)))
                      {:min-x 9999 :min-y 9999 :max-x -9999 :max-y -9999} points)]
    (assoc bound
           :width (- (:max-x bound) (:min-x bound))
           :height (- (:max-y bound) (:min-y bound)))))

;; TODO Now it produces screen coordinates from 0 to 1, multiply by screen-space size
;;      and add necessary paddings. Keep in mind, that svg coordinates start at top-left,
;;      but the plot should start at bottom-left (apply (- 1 y) maybe?).
(defn transform-points [points]
  (let [{min-x :min-x min-y :min-y width :width height :height} (boundary points)]
    (mapv (fn [[x y]] {:screen [(/ (- x min-x) width) (/ (- y min-y) height)]
                       :actual [x y]}) points)))

(defn plot
  ([points [width height]]
   (plot points [width height] {:axes-color "black"}))
  ([points [width height] {axes-color :axes-color}]
   (prn (transform-points points))
   (let [padding-x 10
         padding-y 10

         top-left-corner [padding-x padding-y]
         top-right-corner [(- width padding-x) padding-y]
         bottom-left-corner [padding-x (- height padding-y)]
         bottom-right-corner [(- width padding-x) (- height padding-x)]

         origin bottom-left-corner
         axis-style {:stroke axes-color :stroke-width 2}
         axis-tip-style {:fill axes-color :stroke-width 0}
         ]

     [:svg {:width width :height height}
      (line origin top-left-corner axis-style)
      (line origin bottom-right-corner axis-style)
      (triangle bottom-right-corner 10 10 :right axis-tip-style)
      (triangle top-left-corner 10 10 :up axis-tip-style)
      ])))
