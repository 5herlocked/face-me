package com.amplifyframework.datastore.generated.model;

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

/** This is an auto generated class representing the RegisteredUser type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "RegisteredUsers")
public final class RegisteredUser implements Model {
  public static final QueryField ID = field("RegisteredUser", "id");
  public static final QueryField DISPLAY_NAME = field("RegisteredUser", "displayName");
  public static final QueryField VERIFIED_IMAGE = field("RegisteredUser", "verifiedImage");
  public static final QueryField ACC_OWNER = field("RegisteredUser", "accountOwnerRegisteredUsersId");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String") String displayName;
  private final @ModelField(targetType="AWSURL", isRequired = true) String verifiedImage;
  private final @ModelField(targetType="AccountOwner", isRequired = true) @BelongsTo(targetName = "accountOwnerRegisteredUsersId", type = AccountOwner.class) AccountOwner accOwner;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String getId() {
      return id;
  }
  
  public String getDisplayName() {
      return displayName;
  }
  
  public String getVerifiedImage() {
      return verifiedImage;
  }
  
  public AccountOwner getAccOwner() {
      return accOwner;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private RegisteredUser(String id, String displayName, String verifiedImage, AccountOwner accOwner) {
    this.id = id;
    this.displayName = displayName;
    this.verifiedImage = verifiedImage;
    this.accOwner = accOwner;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      RegisteredUser registeredUser = (RegisteredUser) obj;
      return ObjectsCompat.equals(getId(), registeredUser.getId()) &&
              ObjectsCompat.equals(getDisplayName(), registeredUser.getDisplayName()) &&
              ObjectsCompat.equals(getVerifiedImage(), registeredUser.getVerifiedImage()) &&
              ObjectsCompat.equals(getAccOwner(), registeredUser.getAccOwner()) &&
              ObjectsCompat.equals(getCreatedAt(), registeredUser.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), registeredUser.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getDisplayName())
      .append(getVerifiedImage())
      .append(getAccOwner())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("RegisteredUser {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("displayName=" + String.valueOf(getDisplayName()) + ", ")
      .append("verifiedImage=" + String.valueOf(getVerifiedImage()) + ", ")
      .append("accOwner=" + String.valueOf(getAccOwner()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static VerifiedImageStep builder() {
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
  public static RegisteredUser justId(String id) {
    return new RegisteredUser(
      id,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      displayName,
      verifiedImage,
      accOwner);
  }
  public interface VerifiedImageStep {
    AccOwnerStep verifiedImage(String verifiedImage);
  }
  

  public interface AccOwnerStep {
    BuildStep accOwner(AccountOwner accOwner);
  }
  

  public interface BuildStep {
    RegisteredUser build();
    BuildStep id(String id);
    BuildStep displayName(String displayName);
  }
  

  public static class Builder implements VerifiedImageStep, AccOwnerStep, BuildStep {
    private String id;
    private String verifiedImage;
    private AccountOwner accOwner;
    private String displayName;
    @Override
     public RegisteredUser build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new RegisteredUser(
          id,
          displayName,
          verifiedImage,
          accOwner);
    }
    
    @Override
     public AccOwnerStep verifiedImage(String verifiedImage) {
        Objects.requireNonNull(verifiedImage);
        this.verifiedImage = verifiedImage;
        return this;
    }
    
    @Override
     public BuildStep accOwner(AccountOwner accOwner) {
        Objects.requireNonNull(accOwner);
        this.accOwner = accOwner;
        return this;
    }
    
    @Override
     public BuildStep displayName(String displayName) {
        this.displayName = displayName;
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
    private CopyOfBuilder(String id, String displayName, String verifiedImage, AccountOwner accOwner) {
      super.id(id);
      super.verifiedImage(verifiedImage)
        .accOwner(accOwner)
        .displayName(displayName);
    }
    
    @Override
     public CopyOfBuilder verifiedImage(String verifiedImage) {
      return (CopyOfBuilder) super.verifiedImage(verifiedImage);
    }
    
    @Override
     public CopyOfBuilder accOwner(AccountOwner accOwner) {
      return (CopyOfBuilder) super.accOwner(accOwner);
    }
    
    @Override
     public CopyOfBuilder displayName(String displayName) {
      return (CopyOfBuilder) super.displayName(displayName);
    }
  }
  
}
