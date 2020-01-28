// run neo4j
bin\neo4j console

// backup
bin\neo4j-admin backup --from=192.168.1.1 --backup-dir=D:\backup\graphdb --database=graph.db --pagecache=2G

// restore
bin\neo4j-admin restore --from=D:\backup\graphdb --database=graph.db --force

// dump
bin\neo4j-admin dump --database=graph.db --to=D:\backup\graphdb.dump

// load
bin\neo4j-admin load --from=D:\backup\graphdb --database=graph.db --force
