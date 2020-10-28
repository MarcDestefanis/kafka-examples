package kafka.examples;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class ProducerDemo {
    private static final Logger LOG = LoggerFactory.getLogger(ProducerDemo.class);
    private static final String TOPIC_NAME = "orders";

    public static void main(String[] args) {
        Properties kafkaProps = new Properties();
        kafkaProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        kafkaProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        kafkaProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        KafkaProducer<String, String> producer = new KafkaProducer<>(kafkaProps);

        fireAndForgetMessage(producer, "Fire and forget message...");
        synchronousSend(producer, "Synchronous message...");
        asynchronousSend(producer, "Asynchronous message....");
    }

    public static void fireAndForgetMessage(KafkaProducer<String, String> producer, String message) {
        LOG.info("Fire and forget: {}", message);
        ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC_NAME, message);
        producer.send(record);
        producer.flush();
    }

    public static void synchronousSend(KafkaProducer<String, String> producer, String message) {
        LOG.info("synchronous send: {}", message);
        ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC_NAME, message);
        try {
            LOG.info(producer.send(record).get().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void asynchronousSend(KafkaProducer<String, String> producer, String message) {
        LOG.info("asynchronous send: {}", message);
        ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC_NAME, message);
        producer.send(record, new OrdersProducerCallback());
        producer.flush();
    }

    private static class OrdersProducerCallback implements Callback {

        @Override
        public void onCompletion(RecordMetadata recordMetadata, Exception e) {
            if (e != null) {
                LOG.error(e.toString());
            } // else the message has been sent successfully
        }
    }
}