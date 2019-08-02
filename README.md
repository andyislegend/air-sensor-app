# Corevalue air sensor application

Corevalue R&D project. The main purpose of this application is air scanning. After information is acquired and analyzed application send it to the Google Cloud Iot Core.  

## Getting Started

1. Register in the Google Cloud.
1. Create project.
1. From the Google Cloud IoT Core section of the Google Cloud console, create a device registry.
1. Create telemetry topic.
1. Create subscription on that telemetry topic.
1. Create device in the Google Cloud Iot UI.
1. Use the [generate_keys.sh](https://github.com/GoogleCloudPlatform/java-docs-samples/blob/master/iot/api-client/generate_keys.sh) script to generate your signing keys:&nbsp;&nbsp;./generate_keys.sh<br /> 
Add rsa_cert.pem (public key) to the device with the Google Cloud Iot UI with next key fowmat formant: RS256_X509 and put rsa_private_pkcs8 (private key) on the Raspberry pi file system.
   

### Prerequisites

* Raspberry pi
* Mq-7 air sensor
* GSM module

### Installing

* install [Maven](https://maven.apache.org/) on your computer or use embedded maven.
* install [Google's root certificates](http://pki.google.com/roots.pem) on your device.
* install java 8 on your device.
* install [pi4j library](https://pi4j.com/1.2/install.html ) on your Raspberry pi.

## Deployment

Build project with following command command: 
* mvn clean package (or use embedded maven)
* copy jar file and put it on the raspberry pi file system
* run jar with next command:<br /> 
sudo java -jar app_name.jar -project_id=<project_id><br />
               -cloud_region=<cloud_region><br /> 
               -registry_id=<registry_id><br />
               -device_id=<device_id><br />
               -host_name=<host_name><br />
               -port=<port><br />
               -private_key_file=<private_key_file><br />
               -send_timeout=<send_timeout><br />
               -device_type=<device_type><br />
               -client_type=<client_type>

* example:<br />
sudo java -jar app_name.jar -project_id="tranquil-rex-244713"<br />
               -cloud_region="europe-west1"<br />
               -registry_id="air-sensor-app"<br />
               -device_id="rasp-1"<br />
               -host_name="mqtt.googleapis.com"<br /> 
               -port="8883" -private_key_file="/home/pi/IdeaProjects/rsa_private_pkcs8"<br /> 
               -send_timeout="5000"<br />
               -device_type="RASPBERRY"<br />
               -client_type="MQTT"

## Reading the messages written by application
Open Google Cloud Shell and type following command: <br />
gcloud pubsub subscriptions pull --auto-ack --limit=25 projects/my-iot-project/subscriptions/my-subscription<br />
You can use online Google Cloud Shell or download [Google Cloud SDK](https://cloud.google.com/sdk/install) and do it locally on your machine.

## Remote code execution on a Raspberry pi with IntelliJ IDEA

For remote code execution or debugging you can use Embedded Linux JVM pluggin.
[There](https://medium.com/@menchukanton/setup-intellij-idea-for-remote-debugging-java-code-on-a-raspberry-pi-6e9df09dfb95) you can find a tutorial how to setup IntelliJ IDEA.


