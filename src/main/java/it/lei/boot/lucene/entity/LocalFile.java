package it.lei.boot.lucene.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author lei
 * @version 1.0.0
 */
@Data
public class LocalFile implements Serializable {
    private String fileName;
    private String path;
    private long size;
    private String content;

    /**
     *
     * @param fileName
     * @param path
     * @param size
     * @param content
     */
    public LocalFile(String fileName, String path, long size, String content) {
        this.fileName = fileName;
        this.path = path;
        this.size = size;
        this.content = content;
    }

    public LocalFile() {
    }
}
