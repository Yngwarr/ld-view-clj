(ns viewer.browser.db)

(def default-db
  {:loading? false
   :user {:id nil
          :slug nil
          :name nil}
   :games []})

(comment
  {:loading? true
   :user {:id 2490
          :slug "yngvarr"
          :name "Yngvarr"}
   :games [{:path "/events/ludum-dare/40/snaketris"
            :slug "snaketris"
            :parent 49883
            :name "Snaketris"
            :comments 37
            :result {:overall 661
                     :mood 992
                     :theme 394
                     :graphics 969
                     :humor 757
                     :fun 385
                     :innovation 85}
            :id 53949
            :average {:humor 2.534
                      :theme 3.633
                      :mood 2.598
                      :graphics 2.663
                      :fun 3.5
                      :overall 3.37
                      :innovation 3.918}
            :ratings {:overall 52
                      :fun 50
                      :innovation 51
                      :theme 51
                      :graphics 51
                      :humor 46
                      :mood 48}}
           {:path "/events/ludum-dare/41/remote-bot"
            :slug "remote-bot"
            :parent 73256
            :name "Remote Bot"
            :comments 56
            :result {:overall 197
                     :mood 524
                     :theme 108
                     :audio 512
                     :graphics 491
                     :humor 524
                     :fun 329
                     :innovation 213}
            :id 75672
            :average {:humor 2.989
                      :theme 4.242
                      :mood 3.365
                      :graphics 3.608
                      :fun 3.575
                      :overall 3.867
                      :audio 3.096
                      :innovation 3.775}
            :ratings {:overall 62
                      :fun 62
                      :innovation 62
                      :theme 62
                      :graphics 62
                      :audio 59
                      :humor 49
                      :mood 54}}
           {:path "/events/ludum-dare/42/quicksort"
            :slug "quicksort"
            :parent 97793
            :name "Quicksort"
            :comments 36
            :result {:overall 735
                     :theme 239
                     :humor 721
                     :fun 873
                     :innovation 538}
            :id 98109
            :average {:humor 2.342
                      :theme 3.917
                      :fun 2.938
                      :overall 3.25
                      :innovation 3.229}
            :ratings {:overall 26
                      :fun 26
                      :innovation 26
                      :theme 26
                      :humor 21}}
           {:path "/events/ludum-dare/43/soulmart",
            :slug "soulmart",
            :parent 120415,
            :name "Soulmart",
            :comments 8,
            :result {},
            :id 120579,
            :average {:humor 3.3,
                      :theme 4.3,
                      :graphics 3.9,
                      :fun 2.9,
                      :overall 3.1,
                      :innovation 3.5},
            :ratings {:overall 7,
                      :fun 7,
                      :innovation 7,
                      :theme 7,
                      :graphics 7,
                      :humor 7}}]}
  )
