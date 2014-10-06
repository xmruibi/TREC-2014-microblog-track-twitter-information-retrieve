TREC-2014-microblog-track-twitter-information-retrieve 
======================================================
This is for Temporally-Anchored Ad Hoc Search Task.Each topic consists of a query Q and a time t. The system's task is to return a list relevant tweets up until time t. Each run should consist of up to 1000 results, where each result must refer to a tweet that is published prior to and including the query time defined by the topic. Evaluation will then be conducted by standard IR effectiveness measures. The official metric we will use is mean average precision, but we will report other standard metrics such as R-precision and precision at rank 30.

In this project, I was in charge of query expansion and document expansion with index creation. And I tried to use Wordnet to fetch synonyms and retrieve Web pages’ contents from Google results to expend queries. For document expansion, we did VSM to evaluate similar microblog and retrieved each microblog with 100 similar microblogs. In fact, at first, I came up with an idea about using clustering methods by Mahout API, which although failed finally due to the tense schedule.
