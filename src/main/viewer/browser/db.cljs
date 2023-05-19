(ns viewer.browser.db)

(def default-db
  {:loading? false
   :user {:id nil
          :slug nil
          :name nil}
   :games []})

(comment
  {:slug "snaketris",
   :path "/events/ludum-dare/40/snaketris",
   :name "Snaketris",
   :comments 37,
   :ratings {:overall 52,
             :fun 50,
             :innovation 51,
             :theme 51,
             :graphics 51,
             :humor 46,
             :mood 48},
   :average {:humor 2.534,
             :theme 3.633,
             :mood 2.598,
             :graphics 2.663,
             :fun 3.5,
             :overall 3.37,
             :innovation 3.918},
   :result {:overall 661,
            :mood 992,
            :theme 394,
            :graphics 969,
            :humor 757,
            :fun 385,
            :innovation 85}}
  )
