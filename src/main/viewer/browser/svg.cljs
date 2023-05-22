(ns viewer.browser.svg
  (:require [clojure.string :as str]
            [clojure.core.match :refer [match]]))

;; -------- SHAPES --------

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

(defn circle [[x y] radius style]
  [:circle {:cx x :cy y :r radius :style style}])

(defn draw-plot [points {color :color line-width :line-width}]
  (let [line-style {:stroke color :stroke-width line-width}

        lines
        (pop
         (reduce (fn [acc {pt :screen}]
                   (let [prev (peek acc)
                         mod-acc (if (= prev :<>) acc
                                     (conj (pop acc)
                                           [:line (assoc (second prev)
                                                         :x2 (first pt)
                                                         :y2 (second pt))]))]
                     (conj mod-acc (line pt [nil nil] line-style))))
                 [:<>] points))]
    (into lines
          (mapv (fn [{pt :screen}]
                  (circle pt 6 {:fill color :stroke-width 0}))
                points))))

;; -------- DATA --------

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

(defn data->screen [points dimensions]
  (let [{min-x :min-x min-y :min-y data-width :width data-height :height}
        (boundary points)
        {screen-width :width
         screen-height :height
         padding-x :padding-x
         padding-y :padding-y} dimensions]
    (mapv (fn [[x y]]
            {:screen [(+ padding-x (* screen-width (/ (- x min-x) data-width)))
                      (+ padding-y (* screen-height (- 1 (/ (- y min-y) data-height))))]
             :data [x y]}) points)))

;; -------- PLOT --------

(defn plot
  ([data-points [width height]]
   (plot data-points [width height] {:axes-color "black"}))
  ([data-points [width height] {axes-color :axes-color}]
   (let [padding-x 25
         padding-y 25
         data-padding-x 25
         data-padding-y 25

         top-left-corner [padding-x padding-y]
         top-right-corner [(- width padding-x) padding-y]
         bottom-left-corner [padding-x (- height padding-y)]
         bottom-right-corner [(- width padding-x) (- height padding-x)]

         origin bottom-left-corner
         axis-style {:stroke axes-color :stroke-width 2}
         axis-tip-style {:fill axes-color :stroke-width 0}
         plot-style {:color "purple" :line-width 4}

         points (data->screen data-points
                              {:width (- width (* 2 padding-x) data-padding-x)
                               :height (- height (* 2 padding-y) data-padding-y)
                               :padding-x (+ padding-x data-padding-x)
                               :padding-y padding-y})
         ]

     [:svg.plot {:width width :height height}
      (line origin top-left-corner axis-style)
      (line origin bottom-right-corner axis-style)
      (triangle bottom-right-corner 10 10 :right axis-tip-style)
      (triangle top-left-corner 10 10 :up axis-tip-style)

      (draw-plot points plot-style)
      (prn points)
      ])))
