package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.annotations.HasOne;
import com.amplifyframework.core.model.annotations.BelongsTo;
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
  public static final QueryField PRIMARY_USER = field("AccessEvents", "accountOwnerEventsId");
  public static final QueryField ACCESS_EVENTS_AUTH_USER_ID = field("AccessEvents", "accessEventsAuthUserId");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="RegisteredUser", isRequired = true) @HasOne(associatedWith = "id", type = RegisteredUser.class) RegisteredUser authUser = null;
  private final @ModelField(targetType="AccountOwner", isRequired = true) @BelongsTo(targetName = "accountOwnerEventsId", type = AccountOwner.class) AccountOwner primaryUser;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  private final @ModelField(targetType="ID", isRequired = true) String accessEventsAuthUserId;
  public String getId() {
      return id;
  }
  
  public RegisteredUser getAuthUser() {
      return authUser;
  }
  
  public AccountOwner getPrimaryUser() {
      return primaryUser;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  public String getAccessEventsAuthUserId() {
      return accessEventsAuthUserId;
  }
  
  private AccessEvents(String id, AccountOwner primaryUser, String accessEventsAuthUserId) {
    this.id = id;
    this.primaryUser = primaryUser;
    this.accessEventsAuthUserId = accessEventsAuthUserId;
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
              ObjectsCompat.equals(getPrimaryUser(), accessEvents.getPrimaryUser()) &&
              ObjectsCompat.equals(getCreatedAt(), accessEvents.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), accessEvents.getUpdatedAt()) &&
              ObjectsCompat.equals(getAccessEventsAuthUserId(), accessEvents.getAccessEventsAuthUserId());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getPrimaryUser())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .append(getAccessEventsAuthUserId())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("AccessEvents {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("primaryUser=" + String.valueOf(getPrimaryUser()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()) + ", ")
      .append("accessEventsAuthUserId=" + String.valueOf(getAccessEventsAuthUserId()))
      .append("}")
      .toString();
  }
  
  public static PrimaryUserStep builder() {
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
      id,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      primaryUser,
      accessEventsAuthUserId);
  }
  public interface PrimaryUserStep {
    AccessEventsAuthUserIdStep primaryUser(AccountOwner primaryUser);
  }
  

  public interface AccessEventsAuthUserIdStep {
    BuildStep accessEventsAuthUserId(String accessEventsAuthUserId);
  }
  

  public interface BuildStep {
    AccessEvents build();
    BuildStep id(String id);
  }
  

  public static class Builder implements PrimaryUserStep, AccessEventsAuthUserIdStep, BuildStep {
    private String id;
    private AccountOwner primaryUser;
    private String accessEventsAuthUserId;
    @Override
     public AccessEvents build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new AccessEvents(
          id,
          primaryUser,
          accessEventsAuthUserId);
    }
    
    @Override
     public AccessEventsAuthUserIdStep primaryUser(AccountOwner primaryUser) {
        Objects.requireNonNull(primaryUser);
        this.primaryUser = primaryUser;
        return this;
    }
    
    @Override
     public BuildStep accessEventsAuthUserId(String accessEventsAuthUserId) {
        Objects.requireNonNull(accessEventsAuthUserId);
        this.accessEventsAuthUserId = accessEventsAuthUserId;
        return this;
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
    private CopyOfBuilder(String id, AccountOwner primaryUser, String accessEventsAuthUserId) {
      super.id(id);
      super.primaryUser(primaryUser)
        .accessEventsAuthUserId(accessEventsAuthUserId);
    }
    
    @Override
     public CopyOfBuilder primaryUser(AccountOwner primaryUser) {
      return (CopyOfBuilder) super.primaryUser(primaryUser);
    }
    
    @Override
     public CopyOfBuilder accessEventsAuthUserId(String accessEventsAuthUserId) {
      return (CopyOfBuilder) super.accessEventsAuthUserId(accessEventsAuthUserId);
    }
  }
  
}
