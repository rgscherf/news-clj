(ns news-clj.core
  (:require [news-clj.cli :as cli])
  (:gen-class))

(defn -main
  []
  (do
    cli/process-input
    (System/exit 0)))
