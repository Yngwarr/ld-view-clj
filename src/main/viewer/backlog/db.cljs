(ns viewer.backlog.db
  (:require
    [viewer.backlog.data :refer [backlog-lists]]))

(def default-db
  {;; set true when the data is loading
   :loading? false

   ;; if user is set, checkmark the games they commented on
   :user nil

   ;; if nil, allow user to choose the list
   :selected-list nil

   ;; author lists are populated by me and, maybe, by user
   :author-lists backlog-lists})
