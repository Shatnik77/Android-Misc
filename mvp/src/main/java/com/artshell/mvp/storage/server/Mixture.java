package com.artshell.mvp.storage.server;

import android.support.annotation.NonNull;

/**
 * @author artshell on 17/03/2018
 */

public class Mixture implements Key {

    private String url;
    private String path;

    public Mixture(@NonNull String path) {
        this.path = path;
        this.url = ApiConstants.ENDPOINT + "/" + path;
    }

    @Override
    public String getKey() {
        return String.valueOf(hashCode());
    }

    public String getPath() {
        return path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Mixture mixture = (Mixture) o;

        return url != null ? url.equals(mixture.url) : mixture.url == null;
    }

    @Override
    public int hashCode() {
        return url != null ? url.hashCode() : 0;
    }
}
