package com.ipublic.ntipa.facerecognizer.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

/**
 * A Face.
 */

@Document(collection = "T_FACE")
public class Face implements Serializable {

    @Id
    private String id;

    @Field("label")
    private String label;

    @Field("path")
    private String path;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Face face = (Face) o;

        if (id != null ? !id.equals(face.id) : face.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Face{" +
                "id=" + id +
                ", label='" + label + "'" +
                ", path='" + path + "'" +
                '}';
    }
}
