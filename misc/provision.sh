echo "Installing JDK 7";
sudo apt-get -y install openjdk-7-jdk
which java
java -version

echo "Installing Maven"
INSTALL_DIR=/usr/bin/devtools;
if [ ! -f /tmp/apache-maven-3.2.3-bin.tar.gz ]
then
    echo "going to download the binary";
    wget --no-check-certificate http://mirrors.ibiblio.org/apache/maven/maven-3/3.2.3/binaries/apache-maven-3.2.3-bin.tar.gz -O /tmp/apache-maven-3.2.3-bin.tar.gz
fi;

mkdir -p $INSTALL_DIR

cd $INSTALL_DIR
if [ ! -h $INSTALL_DIR/maven ]
then
    tar -xvzf /tmp/apache-maven-3.2.3-bin.tar.gz
    ln -s $INSTALL_DIR/apache-maven-3.2.3 $INSTALL_DIR/maven
    chmod 755 $INSTALL_DIR/maven

    #adjusting path variable
    echo "" >> /etc/bash.bashrc
    echo "# vagrant provisioned bit on `date`" >> /etc/bash.bashrc
    echo "MAVEN_HOME=$INSTALL_DIR/maven; export MAVEN_HOME; PATH=$PATH:$INSTALL_DIR/maven/bin; export PATH" >> /etc/bash.bashrc
fi;

