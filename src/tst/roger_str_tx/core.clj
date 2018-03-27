(ns tst.roger-str-tx.core
  (:use roger-str-tx.core tupelo.core tupelo.test)
  (:require 
    [clojure.string :as str]
    [clojure.java.io :as io]
    [tupelo.string :as ts]
    )
  )

(dotest

  (let [roger-json (slurp (io/resource "roger.json"))
        roger-edn (tupelo.core/json->edn roger-json)
        ]
    ;(pretty roger-json)
    (spyx (keys roger-edn))
    (spyx (get-in roger-edn [:data :graphQLHub]))
    (nl) (println "-----------------------------------------------------------------------------")
    (spyx (first (get-in roger-edn [:data :hn :topStories])))

    (nl) (println "-----------------------------------------------------------------------------")
    (spyx (keys (first (get-in roger-edn [:data :hn :topStories]))))
    ;   => (:title :url :timeISO :by :kids)

    (let [stories (get-in roger-edn [:data :hn :topStories])]
      (doseq [story stories]
        (let [{:keys [title url timeISO by kids]} story]
          (nl)
          (spyx title)
          (spyx url)
          (spyx timeISO)
          (spyx by)
          (doseq [kid kids]
            (nl) (println ".............................................................................")
            (spyx (keys kid))                               ; (:timeISO :by :text)
            (let [{:keys [timeISO by text]} kid]
              (spyx timeISO)
              (spyx by)
              (println "text => '" (clip-str 77 (ts/collapse-whitespace text)) "...'"))))))))
