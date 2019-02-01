(ns id-generator.core
  (:gen-class)
  (:require
    [nano-id.custom :refer [generate]])
  )

(def base-59-nano-id (generate "123456789abcdefghijkmnopqrstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ"))

(defn generate-new-id []
  "Specify format and length of nano ID"
  (base-59-nano-id 10)
  )

(defn add-underscore [id]
  (str (subs id 0 4) "_" (subs id 4 7)  "_" (subs id 7 10)  )
  )

(defn generate-new-id-with-underscore []
  (add-underscore (generate-new-id))
  )


(defn get-ids []
  "Get specific number of IDs to generate"
  (set (take 30000 (repeatedly generate-new-id)))
  )

(defn write-ids-to-file! [ids]
  "write IDs to file 'ids.txt'"
  (with-open [fileout (clojure.java.io/writer "ids.txt")]
    (doseq [id ids]
    (.write fileout id)
    (.newLine fileout))))

(defn -main
  "Write random nano IDs and save to file"
  [& args]
  (write-ids-to-file! (get-ids))
  )

(def say-hello (fn [] (print "hello!")))
