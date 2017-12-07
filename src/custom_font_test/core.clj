(ns custom-font-test.core
  (:require [clj-pdf.core :as pdf]
            [clj-pdf.graphics-2d :as g2d]
            [clojure.java.io :as io]
            [clojure.pprint :refer [pprint]])
  (:gen-class))

(defn -main
  []
  (let [path "output/test.pdf"]
    (g2d/g2d-register-fonts [[(-> "fonts/" io/resource .getPath) false]])
    (pprint (g2d/get-font-maps))
    (pdf/pdf
     [{:register-system-fonts? true}
      [:phrase "Default font text."]
      [:phrase {:ttf-name "fonts/Strato-linked.ttf"}
       "This is text with custom font. It should look very different."]
      [:graphics {:under true :translate [0 200]}
       (fn [g2d]
         (doto g2d
           (.setColor java.awt.Color/RED)
           (.setFont (java.awt.Font. "Strato" java.awt.Font/PLAIN 20))
           (.drawString "Testing Graphics too" 20 160)))]]

     path)))
