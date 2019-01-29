#### 创建索引
* settings
   *  number_of_shards  
     >每个索引的主分片数，默认值是 5 。这个配置在索引创建后不能修改。     
   *  number_of_replicas 
     > 每个主分片的副本数，默认值是 1 。对于活动的索引库，这个配置可以随时修改。
     
     
     
#### _mapping 




#### 分析与分析器 
```
PUT /my_index
{
    "settings": {
        "analysis": {
            "char_filter": {
                "&_to_and": {
                    "type":       "mapping",
                    "mappings": [ "&=> and "]
            }},
            "filter": {
                "my_stopwords": {
                    "type":       "stop",
                    "stopwords": [ "the", "a" ]
            }},
            "analyzer": {
                "my_analyzer": {
                    "type":         "custom",
                    "char_filter":  [ "html_strip", "&_to_and" ],
                    "tokenizer":    "standard",
                    "filter":       [ "lowercase", "my_stopwords" ]
            }}
}}}


eg : 使用自定义分析器测试数据

    GET /my_index/_analyze
    {
      "analyzer": "my_analyzer",
      "text": ["The quick & brown fox"]
    }

```

* [时间格式处理](https://www.elastic.co/guide/en/elasticsearch/reference/5.6/mapping-date-format.html)