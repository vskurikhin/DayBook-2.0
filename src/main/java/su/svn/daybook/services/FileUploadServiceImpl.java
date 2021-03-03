/*
 * This file was last modified at 2021.03.03 19:19 by Victor N. Skurikhin.
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
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FileUploadServiceImpl implements FileUploadService {

    private final String publicDirectory = System.getProperty("user.dir") + "/public/";

    // this is for single file upload
    public Flux<String> getLines(FilePart filePart) {
        try (FileOutputStream fos = new FileOutputStream(publicDirectory + filePart.filename())) {
            return filePart.content()
                    .map(this::fileToString)
                    .map((String string) -> processAndGetLinesAsList(string, filePart))
                    .flatMapIterable(Function.identity());
        } catch (IOException | RuntimeException e) {
            log.error("getLines: ", e);
        }
        return Flux.empty();
    }

    @Override
    public Flux<String> getLines(Mono<FilePart> filePartMono) {
        return filePartMono.flatMapMany(this::getLines);
    }

    @Nonnull
    private String fileToString(DataBuffer dataBuffer) {

        byte[] bytes = new byte[dataBuffer.readableByteCount()];
        dataBuffer.read(bytes);
        DataBufferUtils.release(dataBuffer);

        return new String(bytes, StandardCharsets.UTF_8);
    }

    private List<String> processAndGetLinesAsList(String string, FilePart filePart) {

        final String fileName = publicDirectory + filePart.filename();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.append(string);
        } catch (IOException | RuntimeException e) {
            log.error("processAndGetLinesAsList: ", e);
        }
        return string.lines().filter(s -> !s.isBlank()).collect(Collectors.toList());
    }
}
