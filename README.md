# hdfbul
A concept project. With data coming from 2 sensors, we find the target's exact location on the grid.

***This Repository includes the Eclipse project with Maven***

### Dependencies

- Java (Tested with Java 8)
- Library dependencies are managed with Maven, can be found in pom.xml (Tested with Maven ver. 3.8.1)
- Apache Kafka (Tested with version 3.3.1, scala is not used so its version does not matter.)

### Build
While on the repository folder run maven to make a clean build of the project
```sh
mvn clean install
```
If everything is fine you will have the compiled target at *target/hdfbul-0.0.1-SNAPSHOT.jar*

### Usage
* ##### **Installing/Running Kafka**

First you will need to have kafka up and running. To get kafka see [Kafka Downloads](https://kafka.apache.org/downloads).

Then extract the tarball like so:
```sh
tar -xzf kafka_2.13-3.3.1.tgz
cd kafka_2.13-3.3.1
```
 Then you need to start first the zookeeper and then the kafka server like so:
 ```sh
 bin/zookeeper-server-start.sh config/zookeeper.properties
 ```
 And you can start the kafka in another terminal
  ```sh
bin/kafka-server-start.sh config/server.properties
 ```
 Even though these instructions are for Linux Systems, its not that different for windows either.
 See [Kafka on Windows](https://www.goavega.com/blog/install-apache-kafka-on-windows/)
* ##### **Using the actual program**
```
java -jar hdfbul.jar
```

This program packs both the sensor and the core data processing unit logic. As such when the program starts the user is asked for the required part.

```sh
Select a procedure by typing "SENSOR" or "CORE"
```
You will need 2 sensors and a core. For testing purposes you can run the program three times and type sensor for two and core for the last.

For sensor processes, you will be asked to input sensor and target position.

And for the core unit you should press **Enter**, when you are sure that the sensors are all good to go.

### Download

You can also get the pre-built application as a .jar executable from [Releases](https://github.com/Nycrera/hdfbul/releases)