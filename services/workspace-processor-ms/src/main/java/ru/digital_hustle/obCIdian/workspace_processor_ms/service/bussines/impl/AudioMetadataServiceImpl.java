package ru.digital_hustle.obCIdian.workspace_processor_ms.service.bussines.impl;

import org.springframework.stereotype.Service;
import ru.digital_hustle.obCIdian.workspace_processor_ms.service.bussines.AudioMetadataService;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.ByteArrayInputStream;

@Service
public class AudioMetadataServiceImpl implements AudioMetadataService {

    @Override
    public Double getDurationSeconds(byte[] wavBytes) {
        try (AudioInputStream audioInputStream =
                     AudioSystem.getAudioInputStream(new ByteArrayInputStream(wavBytes))) {

            long frameLength = audioInputStream.getFrameLength();
            float frameRate = audioInputStream.getFormat().getFrameRate();

            if (frameRate <= 0) {
                return null;
            }

            double duration = frameLength / frameRate;
            return Math.round(duration * 100.0) / 100.0;
        } catch (Exception e) {
            throw new RuntimeException("Failed to calculate WAV duration", e);
        }
    }
}