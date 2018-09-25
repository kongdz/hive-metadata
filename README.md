# hive-metadata
## 查询hive metadata表名
TBLS、TABLE_PARAMS
### TBLS
该表中存储Hive表，视图，索引表的基本信息
### TABLE_PARAMS
该表存储表/视图的属性信息
### 步骤
1.通过TBLS表查询TBL_ID<br>
2.通过TBL_ID到PARTITIONS表查询相应分区名PART_NAME<br>
3.通过TBL_ID到TABLE_PARAMS表查询对应PARAM_KEY（totalSize，numRows，transient_lastDdlTime）的PARAM_VALUE<br>
