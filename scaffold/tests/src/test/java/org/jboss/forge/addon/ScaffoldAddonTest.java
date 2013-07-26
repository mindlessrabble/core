/*
 * Copyright 2012 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Eclipse Public License version 1.0, available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.jboss.forge.addon;

import java.util.Set;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.forge.addon.scaffold.ScaffoldProvider;
import org.jboss.forge.arquillian.AddonDependency;
import org.jboss.forge.arquillian.Dependencies;
import org.jboss.forge.arquillian.archive.ForgeArchive;
import org.jboss.forge.furnace.addons.AddonRegistry;
import org.jboss.forge.furnace.repositories.AddonDependencyEntry;
import org.jboss.forge.furnace.services.ExportedInstance;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class ScaffoldAddonTest
{
   @Deployment
   @Dependencies({
            @AddonDependency(name = "org.jboss.forge.addon:scaffold", version = "2.0.0-SNAPSHOT"),
            @AddonDependency(name = "org.jboss.forge.addon:maven", version = "2.0.0-SNAPSHOT"),
            @AddonDependency(name = "org.jboss.forge.furnace:container-cdi", version = "2.0.0-SNAPSHOT")
   })
   public static ForgeArchive getDeployment()
   {
      ForgeArchive archive = ShrinkWrap
               .create(ForgeArchive.class)
               .addBeansXML()
               .addAsAddonDependencies(
                        AddonDependencyEntry.create("org.jboss.forge.addon:scaffold", "2.0.0-SNAPSHOT"),
                        AddonDependencyEntry.create("org.jboss.forge.furnace:container-cdi", "2.0.0-SNAPSHOT")
               );

      return archive;
   }

   @Inject
   private AddonRegistry registry;

   @Test
   public void testCanLoadScaffoldProviders() throws Exception
   {
      Set<ExportedInstance<ScaffoldProvider>> providerInstances = registry.getExportedInstances(ScaffoldProvider.class);
      Assert.assertFalse(providerInstances.isEmpty());
      Assert.assertNotNull(providerInstances.iterator().next().get());
   }
}
