package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.temporal.Temporal;

import java.util.List;
import java.util.UUID;
import java.util.Objects;

import androidx.core.util.ObjectsCompat;

import com.amplifyframework.core.model.Model;
import com.amplifyframework.core.model.annotations.Index;
import com.amplifyframework.core.model.annotations.ModelConfig;
import com.amplifyframework.core.model.annotations.ModelField;
import com.amplifyframework.core.model.query.predicate.QueryField;

import static com.amplifyframework.core.model.query.predicate.QueryField.field;

/** This is an auto generated class representing the AccessEvents type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "AccessEvents")
public final class AccessEvents implements Model {
  public static final QueryField ID = field("AccessEvents", "id");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String getId() {
      return id;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private AccessEvents(String id) {
    this.id = id;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      AccessEvents accessEvents = (AccessEvents) obj;
      return ObjectsCompat.equals(getId(), accessEvents.getId()) &&
              ObjectsCompat.equals(getCreatedAt(), accessEvents.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), accessEvents.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("AccessEvents {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static BuildStep builder() {
      return new Builder();
  }
  
  /** 
   * WARNING: This method should not be used to build an instance of this object for a CREATE mutation.
   * This is a convenience method to return an instance of the object with only its ID populated
   * to be used in the context of a parameter in a delete mutation or referencing a foreign key
   * in a relationship.
   * @param id the id of the existing item this instance will represent
   * @return an instance of this model with only ID populated
   */
  public static AccessEvents justId(String id) {
    return new AccessEvents(
      id
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id);
  }
  public interface BuildStep {
    AccessEvents build();
    BuildStep id(String id);
  }
  

  public static class Builder implements BuildStep {
    private String id;
    @Override
     public AccessEvents build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new AccessEvents(
          id);
    }
    
    /** 
     * @param id id
     * @return Current Builder instance, for fluent method chaining
     */
    public BuildStep id(String id) {
        this.id = id;
        return this;
    }
  }
  

  public final class CopyOfBuilder extends Builder {
    private CopyOfBuilder(String id) {
      super.id(id);
      
    }
  }
  
}
