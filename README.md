# BourgesPlus
Kibana dashboards for Bourges+ SIEM

A new boolean field `isWorkTime` has to be created for all index-patterns, and populated with the script `isWorkTime.java`.
To do so in Kibana : Stack Management -> Index Patterns -> \<index pattern> -> set-value -> paste the script

To upload the dashboards and visualizations, go to Stack Management -> Saved objects -> import, and import the `saved_objects.ndjson` file
