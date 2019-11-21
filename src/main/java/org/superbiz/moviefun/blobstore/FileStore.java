package org.superbiz.moviefun.blobstore;

import org.apache.tika.Tika;
import org.apache.tika.io.IOUtils;

import java.io.*;
import java.util.Optional;

import static java.lang.String.format;

public class FileStore implements BlobStore {

    private final Tika tika = new Tika();

    @Override
    public void put(Blob blob) throws IOException {
        File targetFile = new File(blob.name);

        targetFile.delete();
        targetFile.getParentFile().mkdirs();
        targetFile.createNewFile();

        /*
        targetFile.delete();
        targetFile.getParentFile().mkdirs();
        targetFile.createNewFile();

        try (FileOutputStream outputStream = new FileOutputStream(targetFile)) {
            outputStream.write(uploadedFile.getBytes());
        }
        */

        try (FileOutputStream outputStream = new FileOutputStream(targetFile)) {

            InputStream inputStream= blob.inputStream;
            int data = 0;
            while ((data = inputStream.read()) != -1){
                outputStream.write(data);
            }
            //outputStream.write(inputStreamReader.ge));
            //IOUtils.copy(blob.inputStream, outputStream);
        }
    }

    @Override
    public Optional<Blob> get(String name) throws IOException {
        File file = new File(name);
        if (!file.exists()) {
            return Optional.empty();
        }

        return Optional.of(new Blob(
                name,
                new FileInputStream(file),
                tika.detect(file)
        ));
    }

    @Override
    public void deleteAll() {
        // ...
    }


}