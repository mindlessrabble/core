package org.jboss.forge.arquillian;

import java.io.File;

import org.jboss.arquillian.container.spi.client.container.DeployableContainer;
import org.jboss.arquillian.container.spi.client.container.DeploymentException;
import org.jboss.arquillian.container.spi.client.container.LifecycleException;
import org.jboss.arquillian.container.spi.client.protocol.ProtocolDescription;
import org.jboss.arquillian.container.spi.client.protocol.metadata.HTTPContext;
import org.jboss.arquillian.container.spi.client.protocol.metadata.ProtocolMetaData;
import org.jboss.arquillian.container.spi.client.protocol.metadata.Servlet;
import org.jboss.forge.arquillian.archive.ForgeArchive;
import org.jboss.forge.arquillian.protocol.ServletProtocolDescription;
import org.jboss.forge.arquillian.util.NativeSystemCall;
import org.jboss.forge.arquillian.util.ShrinkWrapUtil;
import org.jboss.forge.container.AddonUtil;
import org.jboss.forge.container.AddonUtil.AddonEntry;
import org.jboss.forge.container.util.Files;
import org.jboss.forge.container.util.OSUtils;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.descriptor.api.Descriptor;

public class ForgeDeployableContainer implements DeployableContainer<ForgeContainerConfiguration>
{
   private Process process;
   private String FORGE_HOME;

   @Override
   public Class<ForgeContainerConfiguration> getConfigurationClass()
   {
      return ForgeContainerConfiguration.class;
   }

   @Override
   public void setup(ForgeContainerConfiguration configuration)
   {
      FORGE_HOME = configuration.getForgeHome();
   }

   @Override
   public void start() throws LifecycleException
   {
      try
      {
         this.process = NativeSystemCall.exec("java", "-Dforge.logging=false -Dforge.home=" + FORGE_HOME,
                  "-jar", FORGE_HOME + "/jboss-modules.jar", "-modulepath",
                  FORGE_HOME + "/modules:" + OSUtils.getUserHomePath() + "/.forge/plugins:", "org.jboss.forge");
      }
      catch (Exception e)
      {
         throw new LifecycleException("Could not start Forge process.", e);
      }
   }

   @Override
   public void stop() throws LifecycleException
   {
      try
      {
         this.process.destroy();
         int status = this.process.waitFor();
         System.out.println("Forge exited with status: " + status);
      }
      catch (InterruptedException e)
      {
         throw new LifecycleException("Container was interrupted while stopping.", e);
      }
   }

   @Override
   public ProtocolDescription getDefaultProtocol()
   {
      return new ServletProtocolDescription();
   }

   @Override
   public ProtocolMetaData deploy(Archive<?> archive) throws DeploymentException
   {
      AddonEntry addon = getAddonEntry(archive);
      File destDir = AddonUtil.getAddonSlotDir(addon);
      destDir.mkdirs();

      if (!(archive instanceof ForgeArchive))
         throw new IllegalArgumentException(
                  "Invalid Archive type. Ensure that your @Deployment method returns type 'ForgeArchive'.");

      ShrinkWrapUtil.toFile(new File(destDir.getAbsolutePath() + "/" + archive.getName()), archive);
      ShrinkWrapUtil.unzip(destDir, archive);

      addon = AddonUtil.install(addon);

      HTTPContext httpContext = new HTTPContext("localhost", 4141);
      httpContext.add(new Servlet("ArquillianServletRunner", "/ArquillianServletRunner"));
      return new ProtocolMetaData()
               .addContext(httpContext);

   }

   @Override
   public void undeploy(Archive<?> archive) throws DeploymentException
   {
      AddonEntry addon = getAddonEntry(archive);
      AddonUtil.remove(addon);

      File dir = AddonUtil.getAddonBaseDir(addon);
      boolean deleted = Files.delete(dir, true);
      if (!deleted)
         throw new IllegalStateException("Could not delete file [" + dir.getAbsolutePath() + "]");
   }

   private AddonEntry getAddonEntry(Archive<?> archive)
   {
      return new AddonEntry(archive.getName().replaceFirst("\\.jar$", ""), "2.0.0-SNAPSHOT", "main");
   }

   @Override
   public void deploy(Descriptor descriptor) throws DeploymentException
   {
      throw new UnsupportedOperationException("Descriptors not supported by Forge");
   }

   @Override
   public void undeploy(Descriptor descriptor) throws DeploymentException
   {
      throw new UnsupportedOperationException("Descriptors not supported by Forge");
   }

}