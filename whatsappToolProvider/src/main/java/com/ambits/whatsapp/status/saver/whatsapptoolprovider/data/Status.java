package com.ambits.whatsapp.status.saver.whatsapptoolprovider.data;

import static com.ambits.whatsapp.status.saver.whatsapptoolprovider.helpers.DirectoryUtils.VIDEO;

import androidx.documentfile.provider.DocumentFile;


import java.io.File;
import java.io.Serializable;
import java.net.URLConnection;
import java.util.Objects;

public class Status implements Serializable {

    private File file;
    private DocumentFile documentFile;
    //    private Bitmap thumbnail;
    private String title;
    private String path;
    private boolean isVideo;

    public Status(File file, String title, String path) {
        this.file = file;
        this.title = title;
        this.path = path;
        String MP4 = ".mp4";
        this.isVideo = file.getName().endsWith(MP4);
    }

    public Status(String path) {
        this.path = path;
        String MP4 = ".mp4";
        this.isVideo = isVideoFile(path);
    }

    public boolean isVideoFile(String path) {
        String mimeType = URLConnection.guessContentTypeFromName(path);
        return mimeType != null && mimeType.startsWith(VIDEO);
    }

    public Status(DocumentFile file, String title, String path) {
        this.documentFile = file;
        this.title = title;
        this.path = path;
        String MP4 = ".mp4";
        this.isVideo = Objects.requireNonNull(file.getName()).endsWith(MP4);
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

//    public Bitmap getThumbnail() {
//        return thumbnail;
//    }
//
//    public void setThumbnail(Bitmap thumbnail) {
//        this.thumbnail = thumbnail;
//    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isVideo() {
        return isVideo;
    }

    public void setVideo(boolean video) {
        isVideo = video;
    }
}
