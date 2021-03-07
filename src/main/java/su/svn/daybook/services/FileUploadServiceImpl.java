/*
 * This file was last modified at 2021.03.07 12:19 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * FileUploadServiceImpl.java
 * $Id$
 */

package su.svn.daybook.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Nonnull;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FileUploadServiceImpl implements FileUploadService {

    private final UnzipService unzipService;

    private final String publicDirectory = System.getProperty("user.dir") + "/public/";

    public FileUploadServiceImpl(UnzipService unzipService) {
        this.unzipService = unzipService;
    }

    // this is for single file upload
    public Flux<String> getLines(FilePart filePart) {
        try (FileOutputStream fos = new FileOutputStream(publicDirectory + filePart.filename())) {
            return filePart.content()
                    .map(this::fileToBytes)
                    .map(bytes -> processAndGetLinesAsList(bytes, filePart))
                    .flatMapIterable(Function.identity())
                    .doOnComplete(() -> unzipFile(filePart.filename()));
        } catch (IOException | RuntimeException e) {
            log.error("getLines: ", e);
        }
        return Flux.empty();
    }

    private void unzipFile(String filename) {
        unzipService.unzipFile(publicDirectory + filename);
    }

    @Override
    public Flux<String> getLines(Mono<FilePart> filePartMono) {
        return filePartMono.flatMapMany(this::getLines);
    }

    @Nonnull
    private byte[] fileToBytes(DataBuffer dataBuffer) {

        byte[] bytes = new byte[dataBuffer.readableByteCount()];
        dataBuffer.read(bytes);
        DataBufferUtils.release(dataBuffer);

        return bytes;
    }

    private List<String> processAndGetLinesAsList(byte[] bytes, FilePart filePart) {

        final String fileName = publicDirectory + filePart.filename();
        try (RandomAccessFile raf = new RandomAccessFile(fileName, "rw")) {
            raf.seek(raf.length());
            raf.write(bytes);
        } catch (IOException | RuntimeException e) {
            log.error("processAndGetLinesAsList: ", e);
        }
        return new String(bytes).lines().filter(s -> !s.isBlank()).collect(Collectors.toList());
    }
}
