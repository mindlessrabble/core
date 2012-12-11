package org.jboss.forge.container;

import java.util.Arrays;
import java.util.List;

public final class AddonId
{
   private final String name;
   private final String apiVersion;
   private final String version;

   private AddonId(final String name, final String version, final String apiVersion)
   {
      if (name == null || name.isEmpty())
         throw new IllegalArgumentException("Name cannot be null.");
      this.name = name;

      if (version == null || version.isEmpty())
         throw new IllegalArgumentException("Version cannot be null.");
      this.version = version;

      this.apiVersion = apiVersion;
   }

   public String getName()
   {
      return name;
   }

   public String getApiVersion()
   {
      return apiVersion == null ? "" : apiVersion;
   }

   public String getVersion()
   {
      return version;
   }

   @Override
   public String toString()
   {
      return toCoordinates();
   }

   public static AddonId fromCoordinates(final String coordinates)
   {
      String[] split = coordinates.split(",");
      List<String> tokens = Arrays.asList(split);

      if (tokens.size() < 2)
      {
         throw new IllegalArgumentException(
                  "Coordinates must be of the form 'name,version' or 'name,version,api-version");
      }

      if (tokens.size() == 3)
      {
         if (tokens.get(2) == null || tokens.get(2).isEmpty())
            throw new IllegalArgumentException("API version was empty [" + coordinates + "]");
         return from(tokens.get(0), tokens.get(1), tokens.get(2));
      }
      return from(tokens.get(0), tokens.get(1));

   }

   public static AddonId from(String name, String version)
   {
      return from(name, version, null);
   }

   public static AddonId from(String name, String version, String apiVersion)
   {
      return new AddonId(name, version, apiVersion);
   }

   public String toCoordinates()
   {
      return getName() + "," + getVersion() + "," + getApiVersion();
   }

   public String toModuleId()
   {
      return getName().replaceAll(":", ".") + ":" + getVersion();
   }

   @Override
   public int hashCode()
   {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((name == null) ? 0 : name.hashCode());
      result = prime * result + ((version == null) ? 0 : version.hashCode());
      return result;
   }

   @Override
   public boolean equals(Object obj)
   {
      if (this == obj)
         return true;
      if (obj == null)
         return false;
      if (getClass() != obj.getClass())
         return false;
      AddonId other = (AddonId) obj;
      if (name == null)
      {
         if (other.name != null)
            return false;
      }
      else if (!name.equals(other.name))
         return false;
      if (version == null)
      {
         if (other.version != null)
            return false;
      }
      else if (!version.equals(other.version))
         return false;
      return true;
   }

}