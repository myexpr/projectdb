About
==================
A very simple project to track the various technologies used across several projects so that folks dont need to ask the question have we used this technology yet

Conventions
==================
* `MONGO` ==> mongo home location
* `MAVEN` ==> maven home location 
* `CODE`  ==> project home dir

Pre-Requisites
==================
* download and install maven
* download and install mongodb
* download and install eclipse

Installation
==================
* Checkout the repository by cloning git@github.com:myexpr/projectdb.git

* Start mongodb by typing 
```
        $MONGO/bin/mongod
```

* Insert test data by 
```
        $MONGO/bin/mongoimport.exe -d projrepo -c projectdata < $CODE/misc/testdata.json
```

* Verify data has been inserted by
```
        $MONGO/bin/mongo projrepo

        > db.projectdata.count()
        4

        > db.projectdata.find() 
        --gibberish data for the 4 records
```

* Run maven to download all dependencies. This may take a while 
```
        cd $CODE
        $MAVEN/bin/mvn clean compile package -Dspring.active.profile=test
```

* Start the application in the development environment by typing
```
        cd $CODE
        $MAVEN/bin/mvn jetty:start -Dspring.active.profile=dev
```

* Navigate to http://localhost:8080/home

Working with Eclipe
==================
* import workspace into eclipse. 
* You may need to define M2_REPO environment variable for the build paths to work correctly
* project should clean compile 

Production Environment
=================
The production instance (if you can call it) is located on openshift [http://tagcloud-myexperiments.rhcloud.com/home] with the git repository hosted at [ssh://53febe82e0b8cd8418000139@tagcloud-myexperiments.rhcloud.com/~/git/tagcloud.git/]

When you add this as an upstream destination and push any code changes; code is automatically compiled and deployed (assuming we didnt screw up anywhere)


