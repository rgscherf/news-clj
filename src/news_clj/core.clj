(ns news-clj.core
  (:require [news-clj.retrieve :as retrieve])
  (:gen-class))

(defn -main
  []
  (println retrieve/formatted-feeds))
