(ns id-generator.core
  (:gen-class)
  (:require
   [nano-id.custom :refer [generate]])
  )

(def base-59-nano-id (generate "123456789abcdefghijkmnopqrstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ_"))

(defn generate-new-id []
  (base-59-nano-id 8)
  )

(defn get-ids []
  (set (take 30000 (repeatedly generate-new-id)))
  )


(defn write-ids-to-file! [ids]
  (doseq [id ids]
    (spit "ids.txt" (str id "\n") :append true)
    )
  )

(defn -main
  "Writing random ids to file"
  [& args]
  (write-ids-to-file! (get-ids))
  )
