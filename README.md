# Spring WebFlux w/ Reactive Data Stores

The Reactive Spring initiative offers series of non-blocking solutions from back-end to data storages.

Combined them all provides the promise of easy-scalable, fully concurrent application with fewer threads and lower resources consumption.   


### Prerequisites

- JDK 11
- Docker (or equivalent MongoDB, PostgreSQL, Redis instances)

Not so uncommon case is using relational databases for persistence of crucial business information along with NoSQL, which handles large load of less essential data.

The concept model assumes the entities being:
- Trader - PostgreSQL table
- Transaction - MongoDB document

Artificially synced between.

### Reactivity

- Reactive MongoDB (`spring-boot-starter-data-mongodb-reactive`)
- Reactive PostgreSQL (`spring-data-r2dbc w/ r2dbc-postgresql`)
- Spring WebFlux

### The Case

`MarketApplicationTests.java`

The Market scenario consist of registering the Traders via R2DBC in PostgreSQL and therefore providing the load of many concurrent service usages, creating (drafts of) Transactions by Traders in MongoDB (many-writes).

### TODO

- finish up to make app production-ready
