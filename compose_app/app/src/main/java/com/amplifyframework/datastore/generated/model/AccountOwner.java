package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.annotations.HasMany;
import com.amplifyframework.core.model.temporal.Temporal;

import java.util.List;
import java.util.UUID;
import java.util.Objects;

import androidx.core.util.ObjectsCompat;

import com.amplifyframework.core.model.AuthStrategy;
import com.amplifyframework.core.model.Model;
import com.amplifyframework.core.model.ModelOperation;
import com.amplifyframework.core.model.annotations.AuthRule;
import com.amplifyframework.core.model.annotations.Index;
import com.amplifyframework.core.model.annotations.ModelConfig;
import com.amplifyframework.core.model.annotations.ModelField;
import com.amplifyframework.core.model.query.predicate.QueryField;

import static com.amplifyframework.core.model.query.predicate.QueryField.field;

/** This is an auto generated class representing the AccountOwner type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "AccountOwners", authRules = {
  @AuthRule(allow = AuthStrategy.OWNER, ownerField = "owner", identityClaim = "cognito:username", provider = "userPools", operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ })
})
public final class AccountOwner implements Model {
  public static final QueryField ID = field("AccountOwner", "id");
  public static final QueryField FIRST_NAME = field("AccountOwner", "firstName");
  public static final QueryField LAST_NAME = field("AccountOwner", "lastName");
  public static final QueryField DISPLAY_NAME = field("AccountOwner", "displayName");
  public static final QueryField EMAIL = field("AccountOwner", "email");
  public static final QueryField VERIFIED_IMAGE = field("AccountOwner", "verifiedImage");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String") String firstName;
  private final @ModelField(targetType="String") String lastName;
  private final @ModelField(targetType="String") String displayName;
  private final @ModelField(targetType="AWSEmail", isRequired = true) String email;
  private final @ModelField(targetType="AWSURL", isRequired = true) String verifiedImage;
  private final @ModelField(targetType="RegisteredUser") @HasMany(associatedWith = "accOwner", type = RegisteredUser.class) List<RegisteredUser> registeredUsers = null;
  private final @ModelField(targetType="AccessEvents") @HasMany(associatedWith = "primaryUser", type = AccessEvents.class) List<AccessEvents> events = null;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String getId() {
      return id;
  }
  
  public String getFirstName() {
      return firstName;
  }
  
  public String getLastName() {
      return lastName;
  }
  
  public String getDisplayName() {
      return displayName;
  }
  
  public String getEmail() {
      return email;
  }
  
  public String getVerifiedImage() {
      return verifiedImage;
  }
  
  public List<RegisteredUser> getRegisteredUsers() {
      return registeredUsers;
  }
  
  public List<AccessEvents> getEvents() {
      return events;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private AccountOwner(String id, String firstName, String lastName, String displayName, String email, String verifiedImage) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.displayName = displayName;
    this.email = email;
    this.verifiedImage = verifiedImage;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      AccountOwner accountOwner = (AccountOwner) obj;
      return ObjectsCompat.equals(getId(), accountOwner.getId()) &&
              ObjectsCompat.equals(getFirstName(), accountOwner.getFirstName()) &&
              ObjectsCompat.equals(getLastName(), accountOwner.getLastName()) &&
              ObjectsCompat.equals(getDisplayName(), accountOwner.getDisplayName()) &&
              ObjectsCompat.equals(getEmail(), accountOwner.getEmail()) &&
              ObjectsCompat.equals(getVerifiedImage(), accountOwner.getVerifiedImage()) &&
              ObjectsCompat.equals(getCreatedAt(), accountOwner.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), accountOwner.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getFirstName())
      .append(getLastName())
      .append(getDisplayName())
      .append(getEmail())
      .append(getVerifiedImage())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("AccountOwner {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("firstName=" + String.valueOf(getFirstName()) + ", ")
      .append("lastName=" + String.valueOf(getLastName()) + ", ")
      .append("displayName=" + String.valueOf(getDisplayName()) + ", ")
      .append("email=" + String.valueOf(getEmail()) + ", ")
      .append("verifiedImage=" + String.valueOf(getVerifiedImage()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static EmailStep builder() {
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
  public static AccountOwner justId(String id) {
    return new AccountOwner(
      id,
      null,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      firstName,
      lastName,
      displayName,
      email,
      verifiedImage);
  }
  public interface EmailStep {
    VerifiedImageStep email(String email);
  }
  

  public interface VerifiedImageStep {
    BuildStep verifiedImage(String verifiedImage);
  }
  

  public interface BuildStep {
    AccountOwner build();
    BuildStep id(String id);
    BuildStep firstName(String firstName);
    BuildStep lastName(String lastName);
    BuildStep displayName(String displayName);
  }
  

  public static class Builder implements EmailStep, VerifiedImageStep, BuildStep {
    private String id;
    private String email;
    private String verifiedImage;
    private String firstName;
    private String lastName;
    private String displayName;
    @Override
     public AccountOwner build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new AccountOwner(
          id,
          firstName,
          lastName,
          displayName,
          email,
          verifiedImage);
    }
    
    @Override
     public VerifiedImageStep email(String email) {
        Objects.requireNonNull(email);
        this.email = email;
        return this;
    }
    
    @Override
     public BuildStep verifiedImage(String verifiedImage) {
        Objects.requireNonNull(verifiedImage);
        this.verifiedImage = verifiedImage;
        return this;
    }
    
    @Override
     public BuildStep firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }
    
    @Override
     public BuildStep lastName(String lastName) {
        this.lastName = lastName;
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
    private CopyOfBuilder(String id, String firstName, String lastName, String displayName, String email, String verifiedImage) {
      super.id(id);
      super.email(email)
        .verifiedImage(verifiedImage)
        .firstName(firstName)
        .lastName(lastName)
        .displayName(displayName);
    }
    
    @Override
     public CopyOfBuilder email(String email) {
      return (CopyOfBuilder) super.email(email);
    }
    
    @Override
     public CopyOfBuilder verifiedImage(String verifiedImage) {
      return (CopyOfBuilder) super.verifiedImage(verifiedImage);
    }
    
    @Override
     public CopyOfBuilder firstName(String firstName) {
      return (CopyOfBuilder) super.firstName(firstName);
    }
    
    @Override
     public CopyOfBuilder lastName(String lastName) {
      return (CopyOfBuilder) super.lastName(lastName);
    }
    
    @Override
     public CopyOfBuilder displayName(String displayName) {
      return (CopyOfBuilder) super.displayName(displayName);
    }
  }
  
}
