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
  (let [{:keys [ id title publication ]} entry
        entry-str (str
                    id
                    " "
                    title
                    " ("
                    publication
                    ")")]
    (println entry-str)))

(defn print-entries
  [entries]
  (dorun (map print-entry entries)))

(defn entry-by-id
  [id]
  (first (filter #(= id (:id %)) keyed-entries)))

(def process-input
  (do
    (print-entries keyed-entries)
    (flush)
    (let [user-entry (read-string (read-line))
          entry-key (int user-entry)]
      (b/browse-url (:link
                      (entry-by-id entry-key))))))
