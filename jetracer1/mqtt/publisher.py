import paho.mqtt.client as mqtt
import threading
import time


class MqttPublisher:
    def __init__(self, AIrover, brokerIp=None, brokerPort=1883, topic=None):
        self.__brokerIp = brokerIp
        self.__brokerPort = brokerPort
        self.__topic = topic
        self.__client = mqtt.Client()
        self.__client.on_connect = self.__on_connect
        self.__client.on_disconnect = self.__on_disconnect
        self.__stop = False
        self.rover = AIrover

    def __on_connect(self):
        print("** publisher connection **")

    def __on_disconnect(self):
        print("** disconnection **")

    def __publish(self):
        self.__client.connect(self.__brokerIp, self.__brokerPort)
        self.__stop = False
        self.__client.loop_start()
        while not self.__stop:
            message = self.rover.sensorMessage()
            # print(message)
            self.__client.publish(self.__topic, message, retain=False)
            time.sleep(0.1)
        self.__client.loop_stop()

    def start(self):
        thread = threading.Thread(target=self.__publish)
        thread.start()

    def stop(self):
        self.__client.disconnect()
        self.__stop = True


if __name__ == '__main__':
    mqttPublisher = MqttPublisher("192.168.3.179", topic="/sensor")
    mqttPublisher.start()