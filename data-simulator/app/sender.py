import json
from confluent_kafka import Producer
from config import KAFKA_BOOTSTRAP_SERVERS, KAFKA_TOPIC

producer_conf = {
    "bootstrap.servers": KAFKA_BOOTSTRAP_SERVERS
}

producer = Producer(producer_conf)

def delivery_report(err, msg):
    if err:
        print(f"Kafka delivery failed: {err}")
    else:
        print(f"Kafka message sent to {msg.topic()} [{msg.partition()}]")

def data_sender(data: dict):
    producer.produce(
        topic=KAFKA_TOPIC,
        value=json.dumps(data),
        callback=delivery_report
    )
    producer.poll(0)
