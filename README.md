# hive-metadata
查询hive metadata
<br>
TBLS、TABLE_PARAMS<br>
TBLS:该表中存储Hive表，视图，索引表的基本信息<br>
TABLE_PARAMS:该表存储表/视图的属性信息<br>
步骤
1.通过TBLS查询TBL_ID<br>
2.通过PART_NAME到PARTITIONS查询相应PARAM_KEY（totalSize，numRows，transient_lastDdlTime）的PARAM_VALUE
