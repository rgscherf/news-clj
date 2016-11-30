(ns news.retrieve
  (:require [feedparsr.core :as p]))

(def feeds
  ; all of these seem to have :published-date except for wapo
  ["http://rss.cbc.ca/lineup/topstories.xml"
   "http://www.thestar.com/feeds.articles.news.rss"
   "http://www.theverge.com/rss/index.xml"
   "http://www.politico.com/rss/politicopicks.xml"])

(defn get-domain
  [url]
  (if url
    (let [domain-regex #"\.(.*)\.(com|ca)"]
      ; re-find returns a vec of
      ; [full-match, group 1, group 2]
      ; so we just take the second element
      ; which is the domain
      ((re-find domain-regex url) 1))
    nil))

(defn format-entry [entry]
 (array-map :title (:title entry)
            :link (:link entry)
            :publication (get-domain (:link entry))
            :published (:published-date entry)))

(defn formatted-feed-entries
  [url]
  (let [parse-url (p/parse-feed url)
        feed-entries (:entries parse-url)]
    (map format-entry feed-entries)))

(def formatted-feeds
  (pmap formatted-feed-entries feeds))
