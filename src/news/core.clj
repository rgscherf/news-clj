(ns news.core
  (:require [news.retrieve :as retrieve])
  (:gen-class))

(defn -main
  []
  (println retrieve/formatted-feeds))

  
