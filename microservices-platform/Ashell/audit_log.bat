curl -XPUT http://127.0.0.1:9200/_template/template_audit_log
{
    "index_patterns" : ["audit-log-*"],
    "order" : 0,
    "settings" : {
        "number_of_replicas" : 0
    },
    "mappings": {
        "doc": {
            "properties": {
                "operation": {
                    "type": "text",
                    "fields": {
                        "keyword": {
                            "type": "keyword",
                            "ignore_above": 256
                        }
                    },
                    "analyzer": "ik_max_word"
                }
            }
        }
    }
}