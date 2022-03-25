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

public class RatingDialogFragmentArgs implements NavArgs {
  private final HashMap arguments = new HashMap();

  private RatingDialogFragmentArgs() {
  }

  @SuppressWarnings("unchecked")
  private RatingDialogFragmentArgs(HashMap argumentsMap) {
    this.arguments.putAll(argumentsMap);
  }

  @NonNull
  @SuppressWarnings("unchecked")
  public static RatingDialogFragmentArgs fromBundle(@NonNull Bundle bundle) {
    RatingDialogFragmentArgs __result = new RatingDialogFragmentArgs();
    bundle.setClassLoader(RatingDialogFragmentArgs.class.getClassLoader());
    if (bundle.containsKey("breweryDialogName")) {
      String breweryDialogName;
      breweryDialogName = bundle.getString("breweryDialogName");
      if (breweryDialogName == null) {
        throw new IllegalArgumentException("Argument \"breweryDialogName\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("breweryDialogName", breweryDialogName);
    } else {
      __result.arguments.put("breweryDialogName", "Inválido");
    }
    return __result;
  }

  @NonNull
  @SuppressWarnings("unchecked")
  public static RatingDialogFragmentArgs fromSavedStateHandle(
      @NonNull SavedStateHandle savedStateHandle) {
    RatingDialogFragmentArgs __result = new RatingDialogFragmentArgs();
    if (savedStateHandle.contains("breweryDialogName")) {
      String breweryDialogName;
      breweryDialogName = savedStateHandle.get("breweryDialogName");
      if (breweryDialogName == null) {
        throw new IllegalArgumentException("Argument \"breweryDialogName\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("breweryDialogName", breweryDialogName);
    } else {
      __result.arguments.put("breweryDialogName", "Inválido");
    }
    return __result;
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public String getBreweryDialogName() {
    return (String) arguments.get("breweryDialogName");
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public Bundle toBundle() {
    Bundle __result = new Bundle();
    if (arguments.containsKey("breweryDialogName")) {
      String breweryDialogName = (String) arguments.get("breweryDialogName");
      __result.putString("breweryDialogName", breweryDialogName);
    } else {
      __result.putString("breweryDialogName", "Inválido");
    }
    return __result;
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public SavedStateHandle toSavedStateHandle() {
    SavedStateHandle __result = new SavedStateHandle();
    if (arguments.containsKey("breweryDialogName")) {
      String breweryDialogName = (String) arguments.get("breweryDialogName");
      __result.set("breweryDialogName", breweryDialogName);
    } else {
      __result.set("breweryDialogName", "Inválido");
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
    RatingDialogFragmentArgs that = (RatingDialogFragmentArgs) object;
    if (arguments.containsKey("breweryDialogName") != that.arguments.containsKey("breweryDialogName")) {
      return false;
    }
    if (getBreweryDialogName() != null ? !getBreweryDialogName().equals(that.getBreweryDialogName()) : that.getBreweryDialogName() != null) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    int result = 1;
    result = 31 * result + (getBreweryDialogName() != null ? getBreweryDialogName().hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "RatingDialogFragmentArgs{"
        + "breweryDialogName=" + getBreweryDialogName()
        + "}";
  }

  public static final class Builder {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    public Builder(@NonNull RatingDialogFragmentArgs original) {
      this.arguments.putAll(original.arguments);
    }

    public Builder() {
    }

    @NonNull
    public RatingDialogFragmentArgs build() {
      RatingDialogFragmentArgs result = new RatingDialogFragmentArgs(arguments);
      return result;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public Builder setBreweryDialogName(@NonNull String breweryDialogName) {
      if (breweryDialogName == null) {
        throw new IllegalArgumentException("Argument \"breweryDialogName\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("breweryDialogName", breweryDialogName);
      return this;
    }

    @SuppressWarnings({"unchecked","GetterOnBuilder"})
    @NonNull
    public String getBreweryDialogName() {
      return (String) arguments.get("breweryDialogName");
    }
  }
}
