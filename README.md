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
Add rsa_cert.pem (public key) to the device with the Google Cloud Iot UI with next key fowmat formant: RS256_X509 and put rsa_private_pkcs8 (private key) on the Raspberry Pi file system ([File transferring with Raspberry Pi section](#file-transferring-with-raspberry-pi)).
   

### Prerequisites

* Raspberry pi
* Mq-7 air sensor
* GSM module

### Installing

* install [Maven](https://maven.apache.org/) on your computer or use embedded maven.
* install [Google's root certificates](http://pki.google.com/roots.pem) on your device.
* install java 8 on your device.
* install [Pi4j library](https://pi4j.com/1.2/install.html ) on your Raspberry pi.
* install [Putty](https://www.putty.org/).

## Deployment

Build project with following command command: 
* mvn clean package (or use embedded maven)
* copy jar file and put it on the Raspberry Pi file system ([File transferring with Raspberry Pi section](#file-transferring-with-raspberry-pi))
* run jar with next command:

<pre>
sudo java -jar app_name.jar -project_id=project_id
               -cloud_region=cloud_region
               -registry_id=registry_id
               -device_id=device_id
               -host_name=host_name
               -port=port
               -private_key_file=private_key_file
               -send_timeout=send_timeout
               -device_type=device_type
               -client_type=client_type
</pre>

* example:

<pre>
sudo java -jar app_name.jar -project_id="tranquil-rex-244713"
               -cloud_region="europe-west1"
               -registry_id="air-sensor-app"
               -device_id="rasp-1"
               -host_name="mqtt.googleapis.com"
               -port="8883" -private_key_file="/home/pi/IdeaProjects/rsa_private_pkcs8"
               -send_timeout="5000"
               -device_type="RASPBERRY"
               -client_type="MQTT"
</pre>

## Reading the messages written by application
Open Google Cloud Shell and type following command:
<pre>
gcloud pubsub subscriptions pull --auto-ack --limit=25 projects/my-iot-project/subscriptions/my-subscription
</pre>
You can use online Google Cloud Shell or download [Google Cloud SDK](https://cloud.google.com/sdk/install) and do it locally on your machine.

## Remote code execution on a Raspberry pi with IntelliJ IDEA

For remote code execution or debugging you can use Embedded Linux JVM pluggin.
[There](https://medium.com/@menchukanton/setup-intellij-idea-for-remote-debugging-java-code-on-a-raspberry-pi-6e9df09dfb95) you can find a tutorial how to setup IntelliJ IDEA.

## File transferring with Raspberry Pi

To transfer your files to your Raspberry Pi you can use Putty. Open cmd or bash and input next command:
<pre>
pscp target_path userid@server.example.com:destination_path
</pre>
