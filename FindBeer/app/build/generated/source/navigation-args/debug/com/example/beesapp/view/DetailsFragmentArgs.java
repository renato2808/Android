package com.example.beesapp.view;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.lifecycle.SavedStateHandle;
import androidx.navigation.NavArgs;
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;

public class DetailsFragmentArgs implements NavArgs {
  private final HashMap arguments = new HashMap();

  private DetailsFragmentArgs() {
  }

  @SuppressWarnings("unchecked")
  private DetailsFragmentArgs(HashMap argumentsMap) {
    this.arguments.putAll(argumentsMap);
  }

  @NonNull
  @SuppressWarnings("unchecked")
  public static DetailsFragmentArgs fromBundle(@NonNull Bundle bundle) {
    DetailsFragmentArgs __result = new DetailsFragmentArgs();
    bundle.setClassLoader(DetailsFragmentArgs.class.getClassLoader());
    if (bundle.containsKey("breweryListName")) {
      String breweryListName;
      breweryListName = bundle.getString("breweryListName");
      if (breweryListName == null) {
        throw new IllegalArgumentException("Argument \"breweryListName\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("breweryListName", breweryListName);
    } else {
      __result.arguments.put("breweryListName", "Inválido");
    }
    if (bundle.containsKey("breweryListType")) {
      String breweryListType;
      breweryListType = bundle.getString("breweryListType");
      if (breweryListType == null) {
        throw new IllegalArgumentException("Argument \"breweryListType\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("breweryListType", breweryListType);
    } else {
      __result.arguments.put("breweryListType", "Inválido");
    }
    if (bundle.containsKey("breweryListRating")) {
      float breweryListRating;
      breweryListRating = bundle.getFloat("breweryListRating");
      __result.arguments.put("breweryListRating", breweryListRating);
    } else {
      __result.arguments.put("breweryListRating", 0.0F);
    }
    if (bundle.containsKey("breweryListSite")) {
      String breweryListSite;
      breweryListSite = bundle.getString("breweryListSite");
      if (breweryListSite == null) {
        throw new IllegalArgumentException("Argument \"breweryListSite\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("breweryListSite", breweryListSite);
    } else {
      __result.arguments.put("breweryListSite", "Inválido");
    }
    if (bundle.containsKey("breweryListAdress")) {
      String breweryListAdress;
      breweryListAdress = bundle.getString("breweryListAdress");
      if (breweryListAdress == null) {
        throw new IllegalArgumentException("Argument \"breweryListAdress\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("breweryListAdress", breweryListAdress);
    } else {
      __result.arguments.put("breweryListAdress", "Inválido");
    }
    return __result;
  }

  @NonNull
  @SuppressWarnings("unchecked")
  public static DetailsFragmentArgs fromSavedStateHandle(
      @NonNull SavedStateHandle savedStateHandle) {
    DetailsFragmentArgs __result = new DetailsFragmentArgs();
    if (savedStateHandle.contains("breweryListName")) {
      String breweryListName;
      breweryListName = savedStateHandle.get("breweryListName");
      if (breweryListName == null) {
        throw new IllegalArgumentException("Argument \"breweryListName\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("breweryListName", breweryListName);
    } else {
      __result.arguments.put("breweryListName", "Inválido");
    }
    if (savedStateHandle.contains("breweryListType")) {
      String breweryListType;
      breweryListType = savedStateHandle.get("breweryListType");
      if (breweryListType == null) {
        throw new IllegalArgumentException("Argument \"breweryListType\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("breweryListType", breweryListType);
    } else {
      __result.arguments.put("breweryListType", "Inválido");
    }
    if (savedStateHandle.contains("breweryListRating")) {
      float breweryListRating;
      breweryListRating = savedStateHandle.get("breweryListRating");
      __result.arguments.put("breweryListRating", breweryListRating);
    } else {
      __result.arguments.put("breweryListRating", 0.0F);
    }
    if (savedStateHandle.contains("breweryListSite")) {
      String breweryListSite;
      breweryListSite = savedStateHandle.get("breweryListSite");
      if (breweryListSite == null) {
        throw new IllegalArgumentException("Argument \"breweryListSite\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("breweryListSite", breweryListSite);
    } else {
      __result.arguments.put("breweryListSite", "Inválido");
    }
    if (savedStateHandle.contains("breweryListAdress")) {
      String breweryListAdress;
      breweryListAdress = savedStateHandle.get("breweryListAdress");
      if (breweryListAdress == null) {
        throw new IllegalArgumentException("Argument \"breweryListAdress\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("breweryListAdress", breweryListAdress);
    } else {
      __result.arguments.put("breweryListAdress", "Inválido");
    }
    return __result;
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public String getBreweryListName() {
    return (String) arguments.get("breweryListName");
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public String getBreweryListType() {
    return (String) arguments.get("breweryListType");
  }

  @SuppressWarnings("unchecked")
  public float getBreweryListRating() {
    return (float) arguments.get("breweryListRating");
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public String getBreweryListSite() {
    return (String) arguments.get("breweryListSite");
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public String getBreweryListAdress() {
    return (String) arguments.get("breweryListAdress");
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public Bundle toBundle() {
    Bundle __result = new Bundle();
    if (arguments.containsKey("breweryListName")) {
      String breweryListName = (String) arguments.get("breweryListName");
      __result.putString("breweryListName", breweryListName);
    } else {
      __result.putString("breweryListName", "Inválido");
    }
    if (arguments.containsKey("breweryListType")) {
      String breweryListType = (String) arguments.get("breweryListType");
      __result.putString("breweryListType", breweryListType);
    } else {
      __result.putString("breweryListType", "Inválido");
    }
    if (arguments.containsKey("breweryListRating")) {
      float breweryListRating = (float) arguments.get("breweryListRating");
      __result.putFloat("breweryListRating", breweryListRating);
    } else {
      __result.putFloat("breweryListRating", 0.0F);
    }
    if (arguments.containsKey("breweryListSite")) {
      String breweryListSite = (String) arguments.get("breweryListSite");
      __result.putString("breweryListSite", breweryListSite);
    } else {
      __result.putString("breweryListSite", "Inválido");
    }
    if (arguments.containsKey("breweryListAdress")) {
      String breweryListAdress = (String) arguments.get("breweryListAdress");
      __result.putString("breweryListAdress", breweryListAdress);
    } else {
      __result.putString("breweryListAdress", "Inválido");
    }
    return __result;
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public SavedStateHandle toSavedStateHandle() {
    SavedStateHandle __result = new SavedStateHandle();
    if (arguments.containsKey("breweryListName")) {
      String breweryListName = (String) arguments.get("breweryListName");
      __result.set("breweryListName", breweryListName);
    } else {
      __result.set("breweryListName", "Inválido");
    }
    if (arguments.containsKey("breweryListType")) {
      String breweryListType = (String) arguments.get("breweryListType");
      __result.set("breweryListType", breweryListType);
    } else {
      __result.set("breweryListType", "Inválido");
    }
    if (arguments.containsKey("breweryListRating")) {
      float breweryListRating = (float) arguments.get("breweryListRating");
      __result.set("breweryListRating", breweryListRating);
    } else {
      __result.set("breweryListRating", 0.0F);
    }
    if (arguments.containsKey("breweryListSite")) {
      String breweryListSite = (String) arguments.get("breweryListSite");
      __result.set("breweryListSite", breweryListSite);
    } else {
      __result.set("breweryListSite", "Inválido");
    }
    if (arguments.containsKey("breweryListAdress")) {
      String breweryListAdress = (String) arguments.get("breweryListAdress");
      __result.set("breweryListAdress", breweryListAdress);
    } else {
      __result.set("breweryListAdress", "Inválido");
    }
    return __result;
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
        return true;
    }
    if (object == null || getClass() != object.getClass()) {
        return false;
    }
    DetailsFragmentArgs that = (DetailsFragmentArgs) object;
    if (arguments.containsKey("breweryListName") != that.arguments.containsKey("breweryListName")) {
      return false;
    }
    if (getBreweryListName() != null ? !getBreweryListName().equals(that.getBreweryListName()) : that.getBreweryListName() != null) {
      return false;
    }
    if (arguments.containsKey("breweryListType") != that.arguments.containsKey("breweryListType")) {
      return false;
    }
    if (getBreweryListType() != null ? !getBreweryListType().equals(that.getBreweryListType()) : that.getBreweryListType() != null) {
      return false;
    }
    if (arguments.containsKey("breweryListRating") != that.arguments.containsKey("breweryListRating")) {
      return false;
    }
    if (Float.compare(that.getBreweryListRating(), getBreweryListRating()) != 0) {
      return false;
    }
    if (arguments.containsKey("breweryListSite") != that.arguments.containsKey("breweryListSite")) {
      return false;
    }
    if (getBreweryListSite() != null ? !getBreweryListSite().equals(that.getBreweryListSite()) : that.getBreweryListSite() != null) {
      return false;
    }
    if (arguments.containsKey("breweryListAdress") != that.arguments.containsKey("breweryListAdress")) {
      return false;
    }
    if (getBreweryListAdress() != null ? !getBreweryListAdress().equals(that.getBreweryListAdress()) : that.getBreweryListAdress() != null) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    int result = 1;
    result = 31 * result + (getBreweryListName() != null ? getBreweryListName().hashCode() : 0);
    result = 31 * result + (getBreweryListType() != null ? getBreweryListType().hashCode() : 0);
    result = 31 * result + Float.floatToIntBits(getBreweryListRating());
    result = 31 * result + (getBreweryListSite() != null ? getBreweryListSite().hashCode() : 0);
    result = 31 * result + (getBreweryListAdress() != null ? getBreweryListAdress().hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "DetailsFragmentArgs{"
        + "breweryListName=" + getBreweryListName()
        + ", breweryListType=" + getBreweryListType()
        + ", breweryListRating=" + getBreweryListRating()
        + ", breweryListSite=" + getBreweryListSite()
        + ", breweryListAdress=" + getBreweryListAdress()
        + "}";
  }

  public static final class Builder {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    public Builder(@NonNull DetailsFragmentArgs original) {
      this.arguments.putAll(original.arguments);
    }

    public Builder() {
    }

    @NonNull
    public DetailsFragmentArgs build() {
      DetailsFragmentArgs result = new DetailsFragmentArgs(arguments);
      return result;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public Builder setBreweryListName(@NonNull String breweryListName) {
      if (breweryListName == null) {
        throw new IllegalArgumentException("Argument \"breweryListName\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("breweryListName", breweryListName);
      return this;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public Builder setBreweryListType(@NonNull String breweryListType) {
      if (breweryListType == null) {
        throw new IllegalArgumentException("Argument \"breweryListType\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("breweryListType", breweryListType);
      return this;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public Builder setBreweryListRating(float breweryListRating) {
      this.arguments.put("breweryListRating", breweryListRating);
      return this;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public Builder setBreweryListSite(@NonNull String breweryListSite) {
      if (breweryListSite == null) {
        throw new IllegalArgumentException("Argument \"breweryListSite\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("breweryListSite", breweryListSite);
      return this;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public Builder setBreweryListAdress(@NonNull String breweryListAdress) {
      if (breweryListAdress == null) {
        throw new IllegalArgumentException("Argument \"breweryListAdress\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("breweryListAdress", breweryListAdress);
      return this;
    }

    @SuppressWarnings({"unchecked","GetterOnBuilder"})
    @NonNull
    public String getBreweryListName() {
      return (String) arguments.get("breweryListName");
    }

    @SuppressWarnings({"unchecked","GetterOnBuilder"})
    @NonNull
    public String getBreweryListType() {
      return (String) arguments.get("breweryListType");
    }

    @SuppressWarnings({"unchecked","GetterOnBuilder"})
    public float getBreweryListRating() {
      return (float) arguments.get("breweryListRating");
    }

    @SuppressWarnings({"unchecked","GetterOnBuilder"})
    @NonNull
    public String getBreweryListSite() {
      return (String) arguments.get("breweryListSite");
    }

    @SuppressWarnings({"unchecked","GetterOnBuilder"})
    @NonNull
    public String getBreweryListAdress() {
      return (String) arguments.get("breweryListAdress");
    }
  }
}
