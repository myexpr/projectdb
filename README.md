OpenShift
==================
The OpenShift `jbossews` cartridge documentation can be found at:

http://openshift.github.io/documentation/oo_cartridge_guide.html#tomcat

# Conventions
* MONGO ==> mongo home location
* MAVEN ==> maven home location 
* CODE  ==> project home dir

# Pre-Requisites
* download install maven
* download and install mongodb
* download and install eclipse

# Installation
* Checkout the repository by cloning git@github.com:myexpr/projectdb.git

* Start mongodb by typing 
        $MONGO/bin/mongod

* Insert test data by 
        $MONGO/bin/mongoimport.exe -d projrepo -c projectdata < $CODE/misc/testdata.json

* Verify data has been inserted by
        $MONGO/bin/mongo projrepo

        > db.projectdata.count()
        4

        > db.projectdata.find() 
        --gibberish data for the 4 records

* Run maven to download all dependencies. This may take a while 
        cd $CODE
        $MAVEN/bin/mvn clean compile package
        
* Start the application by typing
        cd $CODE
        $MAVEN/bin/mvn compile exec:java -Dexec.mainClass=thoughtwok.projectdb.web.MainController

* Navigate to http://localhost:4567/

# Optional Steps for Working with Eclipe
* import workspace into eclipse. you may need to define M2_REPO environment variable for the build paths to work correctly
* project should clean compile 
* run the MainController class

