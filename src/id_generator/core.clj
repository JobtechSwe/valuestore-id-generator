(ns id-generator.core
  (:gen-class)
  (:require
   [nano-id.custom :refer [generate]]
   [clojure.set :refer [subset?]])
  )

(def base-59-nano-id (generate "123456789abcdefghijkmnopqrstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ_"))

(defn generate-new-id []
  "Specify format and length of nano ID"
  (base-59-nano-id 10)
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








(def base58-alphabet "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz")


(defn int-to-base58
  [num]
  (loop [acc []
         n num
         ]
    (if (pos? n)
      (let [index (rem n 58)
            string (nth base58-alphabet index)]
        (recur
         (cons string acc)
         (quot n 58)
         )
        )
      (apply str acc)
      )
    )
  )

(defn base58-to-number
  [string]
  (when (subset? (set string) (set base58-alphabet) )
    (loop [result 0
           s string
           ]
      (if (seq s)
        (recur (+ (*' result 58) (.indexOf base58-alphabet (str (first s))))
               (rest s))
        result
        )
      ))
  )


(defn get-jt-base58-id-from-number [n]
  (str "JT_" (int-to-base58 n))
  )

(defn get-base58-ids []
  (map get-jt-base58-id-from-number (range 58 30000))
  )


(def example-transaction
  [
   {:concept/id "TEMP_ID_1"
    :concept/description "apa"}

   {:concept/id "TEMP_ID_2"
    :concept/description "banan"}
   ]

  )

(def counter (atom 0))
(defn get-next []
  (swap! counter inc)
  )


(defn replace-temp-id-with-base58-id [n]
  (if  (and (string? n) (.startsWith n "TEMP_ID_"))
    (str "JT_" (int-to-base58 (get-next)))
    n
    )
  )

(defn generate-temp-id []
  (gensym "TEMP_ID_")
  )

;; (clojure.walk/prewalk replace-temp-id-with-base58-id example-transaction)




(defn -main
  "Write random nano IDs and save to file"
  [& args]
  (write-ids-to-file! (get-base58-ids))
  )
