## Project Bookshop

This is repository with application that shows my experimental creations of spring application. This is backend part of application, on *BookshopFE* repository is *angular* frontend. This project has been created rather for learning purposes.

## Description

Project has typical tools for shop. It communicates with frontend layer using spring *RESTful web service*. Application according with good practices is dividing on 3 parts: web layer, service layer and repository layer. In repository layer is using *spring data* to perform operations on database. Service layer contains all utils method and proxy methods between web and repository layers. Of course inside project there are implemented as well tests that covers web and repository layers.

## Motivation

My motivation to create this project is ability to learn.

## Code style

Regarding best practices I try use them in any aspect of project
[![XO code style](https://img.shields.io/badge/code_style-XO-5ed9c7.svg)](https://github.com/sindresorhus/standard)

## Technology/Frameworks/Tools used

- [java](https://www.oracle.com/java/index.html): main programming language
- [spring](https://spring.io/): framework used for better project managing
- [hibernate](http://hibernate.org/): layer that connect application with database
- [maven](https://maven.apache.org/): tool using for manage dependencies and building project
- [tomcat](http://tomcat.apache.org/): server on which I run application
- [oracle express edition](http://www.oracle.com/technetwork/database/database-technologies/express-edition/overview/index.html): database that containing data used in application
- [mongodb](https://www.mongodb.com/): database that persist part of application data
- [eclipse](https://www.eclipse.org/): ide that I use to developing

## On what I am currently working

Currently I am working on provide features connected with logging and registering users.

## What next

In feature I plan add to project new noSQL database for collecting data. I think also about migrate to *spring boot*. Besides it project still do not contains elementary features like possibility of do purchases, administration tools and now I should focus on it ;)

## Installation

#### Tools to install

Download [java 8 or above](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)

And install [java 8 or above](https://docs.oracle.com/javase/8/docs/technotes/guides/install/install_overview.html)

Download [maven](https://maven.apache.org/download.cgi)

And install it [maven](https://maven.apache.org/install.html)

Next step is download [oracle express 11g](http://www.oracle.com/technetwork/database/database-technologies/express-edition/downloads/index.html)

After it download and install [mongodb](https://docs.mongodb.com/manual/installation/)

And install [for windows](https://www.thegeekstuff.com/2008/10/oracle-11g-step-by-step-installation-guide-with-screenshots/) of [for linux](https://askubuntu.com/questions/566734/how-to-install-oracle-11gr2-on-ubuntu-14-04) remember to keep default database port on 1521 and change listener port on different than 8080(because on it should running tomcat)

Download [tomcat 8 or above](https://tomcat.apache.org/download-80.cgi)

And install it for example like here [tomcat](https://www.ntu.edu.sg/home/ehchua/programming/howto/Tomcat_HowTo.html)

Also need ide like [eclipse](https://spring.io/tools) and install it

For easier display database structure is recommended to install [sql developer](http://www.oracle.com/technetwork/developer-tools/sql-developer/downloads/index.html)

#### Prepare environment

First of all create folder and name it for example *RealProjects* and clone this project from github

```code
git clone https://github.com/grkopiec/BookshopBE.git
```

For properly displaying example images on frontend, copy from folder in cloned project *src/main/resources/libs/* folder *images* to user home folder(home/user_name/ in linux, users/user_name/ in windows)

Next open eclipse and import cloned project to workspace, after it create server for application, best way is indicate for it in current installed location on disc. When server is created configure run configurations for tomcat by adding below line to vm arguments(it is also possible change this variable in environment variables and directly in java code)

```code
-Dspring.profiles.active=development
```

After configure ide is time to setup database, first create new one, open command prompt and input

```code
sqlplus sys as sysdba
```
Enter the password defined during install oracle express 11g database, and create new user with database and with below name and password

```code
create user bookshop identified by admin;
```

After it put all privileges on just created user

```code
grant all privileges to bookshop;
```

In linux system it is required to start database by command

```code
startup
```

After this operations left sqlplus by command

```code
exit
```

Now is a time to fill database with data, open sql developer and create new connection with below data

```code
connection name: bookshop
username: bookshop
password: admin
connection name: basic
role: default
hostname: localhost
port: 1521
sid: xe
```

Open project folder and go to catalog src -> main -> resources -> sql and open file schema.sql and copy all sql command and run it on sql developer for bookshop database.

For correctly project working it is required oracle driver in local repository then go to project folder and navigate to src -> main -> resources -> libs and open here command protompt and run below line.

```code
mvn install:install-file -Dfile=ojdbc6.jar -DgroupId=com.oracle -DartifactId=ojdbc6 -Dversion=11.2.0 -Dpackaging=jar
```

After it is required to configure mongodb

First start service in command prompt

```code
service mongodb start
```

And to create administrator user start mongodb shell by command

```code
mongo
```

After run shell go to admin database

```code
use admin
```

And create admin user

```code
db.createUser(
  {
    user: "admin",
    pwd: "admin",
    roles: [ { role: "userAdminAnyDatabase", db: "admin" } ]
  }
)
```

Next create new database and select to it

```code
use bookshop
```

After created database create user to who own it

```code
db.createUser(
  {
    user: "bookshop",
    pwd: "admin",
    roles: [ { role: "readWrite", db: "bookshop" } ]
  }
)
```

After it create new collections in bookshop database by importing it from files, to do it move to in terminal to project catalog and run this command

```code
mongoimport --db bookshop --collection usersDetails --file src/main/resources/schemas/usersDetailsMongo.json
mongoimport --db bookshop --collection ordersItmes --file src/main/resources/schemas/ordersItmesMongo.json
```

Now by run this command enable authentication for mongodb(configuration file can be in difference directories in various operating systems)

```code
mongod --auth --config /etc/mongodb.conf
```

#### Run project

After all this configuration it is possible to run project in eclipse click on project and select Run As -> Run on Server

## Run tests

To run all tests it is required configure environment and tools, after do it open eclipse click on project and select Run As -> JUnit test
