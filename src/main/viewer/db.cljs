(ns viewer.db)

(def default-db
  {;; if user is set, checkmark the games they commented on
   :user nil

   ;; if nil, allow user to choose the list
   :selected-list nil

   ;; the lists are populated from hard drive (maybe, from localStorage too)
   :lists {:pixel-prophecy
           {:yngvarr "The day"
            :fgeva "The night"
            :commanderstitch "The chaos"}}})
