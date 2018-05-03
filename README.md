# Web-Crawling

**Goal**

Implementing Web Crawler to measure aspects of a crawl and gather webpage metadata, all from pre-selected news websites.

Enhanced the crawler so that it can collect information about URLs it attempts to fetch and files it successfully downloads. Gathered different statistics about fetched documents to record its behavior.

This crawler is built upon the open source crawler4j library.

**Pre-defined requirements**

The maximum pages to fetch can be set in crawler4j and it should be set to 20,000 to ensure a reasonable execution time for this exercise. Also, maximum depth should be set to 16 to ensure that we limit the crawling.

**Collecting Statistics**

1. The URLs it attempts to fetch, a two column spreadsheet, column 1 containing the URL and
column 2 containing the HTTP status code received; name the file fetch_NewsSite.csv
2. The files it successfully downloads, a four column spreadsheet, column 1 containing the URLs successfully downloaded, column 2 containing the size of the downloaded file(Bytes, or you can choose your own preferred unit (bytes,kb,mb)),
column 3 containing the # of outlinks found, and column 4 containing the resulting content-type; name the file visit_NewsSite.csv
3. All of the URLs (including repeats) that were discovered and processed in some way; a two
column spreadsheet where column 1 contains the encountered URL and column two an indicator of whether the URL a. resides in the website (OK), or b. points outside of the website (N_OK).Name the file urls_NewsSite.csv.


