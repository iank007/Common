// Count all
match (n) return count(n);

// Delete with Limit
match (n)
  with n limit 1000 detach delete n;

// Merge
// Update when exists else insert
merge (n:Person { personId: 'foo'})
  on match set n.personName = 'Januar', n.updatedBy = 'Januar'
  on create set n.personName = 'Januar', n.createdBy = 'Januar';

// Create relationships
match (p1:Person { personName: 'Januar'})
, match (p2:Person {personName: 'Foo'})
create (p1)-[:KNOW]->(p2);

// load CSV using periodic
using periodic commit 1000
load csv with header from 'file:///file_name.csv' as row
return row;

// load csv using periodic 2
// using apoc.map to parse text into map/key-value
// using FOREACH for case-when
SING PERIODIC COMMIT 1000
LOAD CSV WITH HEADERS FROM 'file:///file_name.csv' as row
WITH apoc.map.fromValues([row.start_datetime, row.detail]) as map, row
   MERGE (a:PHONE {no_phone: row.no_from})
   MERGE (b:PHONE {no_phone: row.no_to})
 FOREACH (n IN (CASE WHEN row.direction = 'OUTGOING' THEN [1] ELSE [] END) |
  MERGE (a)-[r:call]->(b) ON CREATE SET r = map, r.count = 1
    ON MATCH SET r += map, r.count = SIZE(KEYS(r)) - 1 )
 FOREACH (n IN (CASE WHEN row.direction = 'INCOMING' THEN [1] ELSE [] END) |
  MERGE (a)<-[r:call]-(b) ON CREATE SET r = map, r.count = 1
     ON MATCH SET r += map, r.count = SIZE(KEYS(r)) - 1 )
RETURN COUNT(row) AS t;

// create constraint
CREATE CONSTRAINT ON (travel:TRAVEL) ASSERT (travel.travel_id, travel.travel_name) IS NODE KEY;

// optional match
// several match
optional match (p:Phone) with collect(p) as c1
optional match (a:Address) with collect(a) + c1 as c2
optional match (s:School) with collect(s) + c2 as c3
unwind c3 as n
match (n)-[r]-(u:Student)
delete r return count(r) as m;
