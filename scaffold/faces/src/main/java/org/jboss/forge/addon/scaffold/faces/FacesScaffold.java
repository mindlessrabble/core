package org.jboss.forge.addon.scaffold.faces;

import java.util.List;

import org.jboss.forge.addon.facets.AbstractFacet;
import org.jboss.forge.addon.facets.constraints.RequiresFacet;
import org.jboss.forge.addon.javaee.facets.CDIFacet;
import org.jboss.forge.addon.javaee.facets.EJBFacet;
import org.jboss.forge.addon.javaee.facets.PersistenceFacet;
import org.jboss.forge.addon.projects.Project;
import org.jboss.forge.addon.projects.facets.DependencyFacet;
import org.jboss.forge.addon.resource.DirectoryResource;
import org.jboss.forge.addon.resource.Resource;
import org.jboss.forge.addon.scaffold.ScaffoldProvider;
import org.jboss.forge.addon.ui.wizard.UIWizardStep;

@RequiresFacet({
    DependencyFacet.class,
    PersistenceFacet.class,
    EJBFacet.class,
    CDIFacet.class
})
public class FacesScaffold extends AbstractFacet<Project> implements ScaffoldProvider {

	@Override
	public boolean install() {
		return true;
	}

	@Override
	public boolean isInstalled() {
		return true;
	}

	@Override
	public boolean uninstall() {
		return true;
	}

	@Override
	public String getName() {
		return "Faces";
	}

	@Override
	public String getDescription() {
		return "Faces scaffolding from JPA entities";
	}

	@Override
	public List<Resource<?>> setup(DirectoryResource targetDir,
			boolean overwrite, boolean installTemplates) {
		return null;
	}

	@Override
	public List<Resource<?>> generateFrom(List<Resource<?>> resource,
			DirectoryResource targetDir, boolean overwrite) {
		return null;
	}

	@Override
	public Class<? extends UIWizardStep> setupStep() {
		return FacesScaffoldSetup.class;
	}

	@Override
	public Class<? extends UIWizardStep> selectResourcesStep() {
		return JPAResourcesStep.class;
	}

}
