# Social Medical Network

The Back-End part of my graduation project - Social medical network **"Concilium"**. The server uses Spring Boot 2.0 with PostgreSQL 10.

## Android application
See this [link](https://github.com/belogurow/Diploma-client) for android app of this social medical network. 

## Building the project
Install PostgreSQL 10. Can use this [tutorial](https://gist.github.com/alistairewj/8aaea0261fe4015333ddf8bed5fe91f8#file-install-postgres-10-ubuntu-md). 

Add it apt repository.
* Ubuntu 14.04: ``sudo add-apt-repository 'deb http://apt.postgresql.org/pub/repos/apt/ trusty-pgdg main'``
* Ubuntu 16.04: ``sudo add-apt-repository 'deb http://apt.postgresql.org/pub/repos/apt/ xenial-pgdg main'``
* Ubuntu 17.04: ``sudo add-apt-repository 'deb http://apt.postgresql.org/pub/repos/apt/ zesty-pgdg main'``

Now import the repository signing key, followed by an update to the package lists:
```bash
wget --quiet -O - https://www.postgresql.org/media/keys/ACCC4CF8.asc | \
  sudo apt-key add -
sudo apt-get update
sudo apt-get install postgres-10
```

Ensure that the server is started by switching to the postgres user.

```bash
sudo su - postgres
/usr/lib/postgresql/10/bin/pg_ctl -D /var/lib/postgresql/10/main -l logfile start
```

Create new database ``diploma_server`` like this:
```bash
psql
CREATE ROLE diploma_server SUPERUSER LOGIN REPLICATION CREATEDB CREATEROLE;
ALTER USER diploma_server WITH PASSWORD 'diploma_server';
CREATE DATABASE diploma_server ENCODING='UTF8' OWNER diploma_server;
\q
\logout
```

(Optional) Create new database ``diploma_server_test`` for tests:

```bash
psql
CREATE DATABASE diploma_server_test ENCODING='UTF8' OWNER diploma_server;
ALTER USER diploma_server WITH PASSWORD 'diploma_server';
\q
\logout
```
Clone this repository, edit the ``application.yml`` file if you use database with another name. Run the project.