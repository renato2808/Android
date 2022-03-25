package com.example.beesapp;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;

public class NavHostDirections {
  private NavHostDirections() {
  }

  @NonNull
  public static HomeToDetails homeToDetails() {
    return new HomeToDetails();
  }

  @NonNull
  public static DetailsToRating detailsToRating() {
    return new DetailsToRating();
  }

  public static class HomeToDetails implements NavDirections {
    private final HashMap arguments = new HashMap();

    private HomeToDetails() {
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public HomeToDetails setBreweryListName(@NonNull String breweryListName) {
      if (breweryListName == null) {
        throw new IllegalArgumentException("Argument \"breweryListName\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("breweryListName", breweryListName);
      return this;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public HomeToDetails setBreweryListType(@NonNull String breweryListType) {
      if (breweryListType == null) {
        throw new IllegalArgumentException("Argument \"breweryListType\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("breweryListType", breweryListType);
      return this;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public HomeToDetails setBreweryListRating(float breweryListRating) {
      this.arguments.put("breweryListRating", breweryListRating);
      return this;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public HomeToDetails setBreweryListSite(@NonNull String breweryListSite) {
      if (breweryListSite == null) {
        throw new IllegalArgumentException("Argument \"breweryListSite\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("breweryListSite", breweryListSite);
      return this;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public HomeToDetails setBreweryListAdress(@NonNull String breweryListAdress) {
      if (breweryListAdress == null) {
        throw new IllegalArgumentException("Argument \"breweryListAdress\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("breweryListAdress", breweryListAdress);
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    @NonNull
    public Bundle getArguments() {
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

    @Override
    public int getActionId() {
      return R.id.homeToDetails;
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

    @Override
    public boolean equals(Object object) {
      if (this == object) {
          return true;
      }
      if (object == null || getClass() != object.getClass()) {
          return false;
      }
      HomeToDetails that = (HomeToDetails) object;
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
      if (getActionId() != that.getActionId()) {
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
      result = 31 * result + getActionId();
      return result;
    }

    @Override
    public String toString() {
      return "HomeToDetails(actionId=" + getActionId() + "){"
          + "breweryListName=" + getBreweryListName()
          + ", breweryListType=" + getBreweryListType()
          + ", breweryListRating=" + getBreweryListRating()
          + ", breweryListSite=" + getBreweryListSite()
          + ", breweryListAdress=" + getBreweryListAdress()
          + "}";
    }
  }

  public static class DetailsToRating implements NavDirections {
    private final HashMap arguments = new HashMap();

    private DetailsToRating() {
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public DetailsToRating setBreweryDialogName(@NonNull String breweryDialogName) {
      if (breweryDialogName == null) {
        throw new IllegalArgumentException("Argument \"breweryDialogName\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("breweryDialogName", breweryDialogName);
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    @NonNull
    public Bundle getArguments() {
      Bundle __result = new Bundle();
      if (arguments.containsKey("breweryDialogName")) {
        String breweryDialogName = (String) arguments.get("breweryDialogName");
        __result.putString("breweryDialogName", breweryDialogName);
      } else {
        __result.putString("breweryDialogName", "Inválido");
      }
      return __result;
    }

    @Override
    public int getActionId() {
      return R.id.detailsToRating;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public String getBreweryDialogName() {
      return (String) arguments.get("breweryDialogName");
    }

    @Override
    public boolean equals(Object object) {
      if (this == object) {
          return true;
      }
      if (object == null || getClass() != object.getClass()) {
          return false;
      }
      DetailsToRating that = (DetailsToRating) object;
      if (arguments.containsKey("breweryDialogName") != that.arguments.containsKey("breweryDialogName")) {
        return false;
      }
      if (getBreweryDialogName() != null ? !getBreweryDialogName().equals(that.getBreweryDialogName()) : that.getBreweryDialogName() != null) {
        return false;
      }
      if (getActionId() != that.getActionId()) {
        return false;
      }
      return true;
    }

    @Override
    public int hashCode() {
      int result = 1;
      result = 31 * result + (getBreweryDialogName() != null ? getBreweryDialogName().hashCode() : 0);
      result = 31 * result + getActionId();
      return result;
    }

    @Override
    public String toString() {
      return "DetailsToRating(actionId=" + getActionId() + "){"
          + "breweryDialogName=" + getBreweryDialogName()
          + "}";
    }
  }
}
