(ns news-clj.retrieve
  (:require [feedparsr.core :as p]
            [clj-time.core :as t]
            [clj-time.format :as f]
            [clj-time.coerce :as coerce]))

(def feeds
  ; all of these seem to have :published-date except for wapo
  ["http://rss.cbc.ca/lineup/topstories.xml"
   "http://www.thestar.com/feeds.articles.news.rss"
   "http://www.theverge.com/rss/index.xml"
   "http://www.politico.com/rss/politicopicks.xml"])


;; GET DOMAIN

(defn- domain
  [url]
  (if url
    (let [domain-regex #"\.(.*)\.(com|ca)"]
      ; re-find returns a vec of
      ; [full-match, group 1, group 2]
      ; so we just take the second element
      ; which is the domain
      ((re-find domain-regex url) 1))
    nil))


;; DATE PARSING

(defn- chop-date
  [date-string]
  (let [date-template #"(.*)\."]
    ((re-find date-template date-string) 1)))

(defn- parse-rss-date
  "take date string, convert to date obj, and to unix time"
  [raw-date]
  (->>
    raw-date
    chop-date
    (f/parse (f/formatters :date-hour-minute-second))
    coerce/to-long))


;; PUT ENTRIES TOGETHER

(defn- format-entry [entry]
 (array-map :title (:title entry)
            :link (:link entry)
            :publication (domain (:link entry))
            :published (parse-rss-date (:published-date entry))))

(defn- formatted-feed-entries
  [url]
  (let [parse-url (p/parse-feed url)
        feed-entries (:entries parse-url)]
    (map format-entry feed-entries)))

(def formatted-feeds
  (pmap formatted-feed-entries feeds))

(prn (formatted-feed-entries (feeds 2)))
(type (parse-rss-date "2016-11-30T10:00:00.000-00:00"))
