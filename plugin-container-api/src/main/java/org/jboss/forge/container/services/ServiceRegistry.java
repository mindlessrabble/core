/*
 * Copyright 2012 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Eclipse Public License version 1.0, available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.jboss.forge.container.services;

import java.util.List;

/**
 * Contains the collection of all installed and available {@link ServiceType} instances.
 * 
 * @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 */
public interface ServiceRegistry
{
   void addService(ServiceType service);

   ServiceType getService(Class<?> clazz);

   List<ServiceType> getServices();
}