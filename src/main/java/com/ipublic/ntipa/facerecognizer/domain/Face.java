package com.ipublic.ntipa.facerecognizer.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

/**
 * A Face.
 */

@Document(collection = "T_FACE")
@CompoundIndexes(value = {
		@CompoundIndex(name = "count_unique", def = "{'count':1 }", unique=true) 
})
public class Face implements Serializable {

    @Id
    private String id;

    @Field("label")
    private String label;

    @Field("path")
    private String path;
    
    @Field("photo")
    private String photo;
    
    @Field("count")
    @Indexed(unique=true)
    private Integer count;
   
    
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

    
    public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public void setPath(String path) {
        this.path = path;
    }

	
	
    public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
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
		return "Face [id=" + id + ", label=" + label + ", path=" + path
				+ ", count=" + count + "]";
	}

  
}
