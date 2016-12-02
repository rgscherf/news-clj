(ns news-clj.cli
  (:require [news-clj.retrieve :as retrieve]
            [clojure.java.browse :as b]))

(def keyed-entries
  (map (fn [i entry]
          (assoc entry :id (inc i)))
       (range)
       retrieve/formatted-feeds))

(defn print-entry
  [entry]
  (let [{:keys [ id title publication link]} entry
        upper-title (.toUpperCase title)]
    (println (str upper-title " " publication))
    (println (str link "\n"))))

(defn print-entries
  [entries]
  (dorun (map print-entry entries)))

(defn entry-by-id
  [id]
  (first (filter #(= id (:id %)) keyed-entries)))

(def process-input
  (do
    (print-entries keyed-entries)))
