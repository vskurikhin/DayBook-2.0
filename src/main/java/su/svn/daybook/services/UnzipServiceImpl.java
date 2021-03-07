/*
 * This file was last modified at 2021.03.07 12:19 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * UnzipServiceImpl.java
 * $Id$
 */

package su.svn.daybook.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Slf4j
@Service
public class UnzipServiceImpl implements UnzipService {

    private final String publicDirectory = System.getProperty("user.dir") + "/public/";

    @Override
    public void unzipFile(String fileName) {
        log.debug("unzipFile({})", fileName);
        byte[] buffer = new byte[1024];
        try (FileInputStream fis = new FileInputStream(fileName)) {

            ZipInputStream zis = new ZipInputStream(fis);
            ZipEntry entry;

            // while there are entries I process them
            while ((entry = zis.getNextEntry()) != null) {
                log.info("entry: {}, {}", entry.getName(), entry.getSize());
                // consume all the data from this entry
                File newFile = newFile(new File(publicDirectory), entry);
                if (entry.isDirectory()) {
                    if (!newFile.isDirectory() && !newFile.mkdirs()) {
                        throw new IOException("Failed to create directory " + newFile);
                    }
                } else {
                    // fix for Windows-created archives
                    File parent = newFile.getParentFile();
                    if ( ! parent.isDirectory() && ! parent.mkdirs()) {
                        throw new IOException("Failed to create directory " + parent);
                    }

                    // write file content
                    FileOutputStream fos = new FileOutputStream(newFile);
                    int len;
                    while ((len = zis.read(buffer)) > 0) {
                        fos.write(buffer, 0, len);
                    }
                    fos.close();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {
        File destFile = new File(destinationDir, zipEntry.getName());

        String destDirPath = destinationDir.getCanonicalPath();
        String destFilePath = destFile.getCanonicalPath();

        if (!destFilePath.startsWith(destDirPath + File.separator)) {
            throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
        }

        return destFile;
    }
}
