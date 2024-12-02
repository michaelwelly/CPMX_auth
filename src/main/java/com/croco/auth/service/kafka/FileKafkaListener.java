package com.croco.auth.service.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Component
public class FileKafkaListener {

    private static final String FILE_SAVE_DIR = "uploads/";

    @KafkaListener(topics = "${spring.kafka.data.files.topic}",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "kafkaFilesListenerContainerFactory")
    public void listen(ConsumerRecord<String, byte[]> record) {
        String filename = record.key();
        byte[] fileData = record.value();

        try {
            // Создание папки, если она не существует
            File uploadDir = new File(FILE_SAVE_DIR);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            // Сохранение файла
            try (FileOutputStream fos = new FileOutputStream(new File(uploadDir, filename))) {
                fos.write(fileData);
            }

            System.out.println("File saved: " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}