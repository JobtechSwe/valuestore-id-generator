(defproject id-generator "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [nano-id "0.9.3"]
                 ]
  :main ^:skip-aot id-generator.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
