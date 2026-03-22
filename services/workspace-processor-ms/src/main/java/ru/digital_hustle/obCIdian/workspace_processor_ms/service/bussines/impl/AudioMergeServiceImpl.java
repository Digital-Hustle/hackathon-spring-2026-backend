package ru.digital_hustle.obCIdian.workspace_processor_ms.service.bussines.impl;

import org.springframework.stereotype.Service;
import ru.digital_hustle.obCIdian.workspace_processor_ms.dto.AudioChunk;
import ru.digital_hustle.obCIdian.workspace_processor_ms.service.bussines.AudioMergeService;

import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class AudioMergeServiceImpl implements AudioMergeService {

    @Override
    public byte[] merge(List<AudioChunk> chunks, int pauseMs) {
        try {
            if (chunks.isEmpty()) {
                throw new IllegalArgumentException("Audio chunks are empty");
            }

            ByteArrayOutputStream pcmOut = new ByteArrayOutputStream();
            AudioFormat targetFormat = null;

            for (int i = 0; i < chunks.size(); i++) {
                AudioInputStream ais = AudioSystem.getAudioInputStream(
                        new ByteArrayInputStream(chunks.get(i).wavData())
                );

                if (targetFormat == null) {
                    targetFormat = ais.getFormat();
                } else if (!isSameFormat(targetFormat, ais.getFormat())) {
                    throw new IllegalStateException("Incompatible WAV formats");
                }

                pcmOut.write(ais.readAllBytes());

                if (i < chunks.size() - 1) {
                    pcmOut.write(createSilenceBytes(targetFormat, pauseMs));
                }
            }

            byte[] pcmBytes = pcmOut.toByteArray();
            long frameLength = pcmBytes.length / targetFormat.getFrameSize();

            AudioInputStream mergedStream = new AudioInputStream(
                    new ByteArrayInputStream(pcmBytes),
                    targetFormat,
                    frameLength
            );

            ByteArrayOutputStream wavOut = new ByteArrayOutputStream();
            AudioSystem.write(mergedStream, AudioFileFormat.Type.WAVE, wavOut);

            return wavOut.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Failed to merge WAV chunks", e);
        }
    }

    private boolean isSameFormat(AudioFormat a, AudioFormat b) {
        return a.getEncoding().equals(b.getEncoding())
                && a.getSampleRate() == b.getSampleRate()
                && a.getSampleSizeInBits() == b.getSampleSizeInBits()
                && a.getChannels() == b.getChannels()
                && a.getFrameSize() == b.getFrameSize()
                && a.getFrameRate() == b.getFrameRate()
                && a.isBigEndian() == b.isBigEndian();
    }

    private byte[] createSilenceBytes(AudioFormat format, int pauseMs) {
        int frameSize = format.getFrameSize();
        int frames = (int) ((format.getFrameRate() * pauseMs) / 1000);
        return new byte[frames * frameSize];
    }
}