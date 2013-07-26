package org.jboss.forge.addon.scaffold.faces;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.jboss.forge.addon.resource.DirectoryResource;
import org.jboss.forge.addon.resource.Resource;
import org.jboss.forge.addon.scaffold.ScaffoldProvider;
import org.jboss.forge.addon.ui.context.UIBuilder;
import org.jboss.forge.addon.ui.context.UIContext;
import org.jboss.forge.addon.ui.context.UIValidationContext;
import org.jboss.forge.addon.ui.input.UIInput;
import org.jboss.forge.addon.ui.metadata.UICommandMetadata;
import org.jboss.forge.addon.ui.metadata.WithAttributes;
import org.jboss.forge.addon.ui.result.NavigationResult;
import org.jboss.forge.addon.ui.result.Result;
import org.jboss.forge.addon.ui.result.Results;
import org.jboss.forge.addon.ui.util.Metadata;
import org.jboss.forge.addon.ui.wizard.UIWizardStep;

public class JPAResourcesStep implements UIWizardStep {

	@Inject
	ScaffoldProvider provider;

	@Inject
	@WithAttributes(label = "Target directory")
	UIInput<DirectoryResource> targetDir;

	@Inject
	@WithAttributes(label = "Overwrite?")
	UIInput<Boolean> overwrite;

	@Override
	public NavigationResult next(UIContext context) throws Exception {
		return null;
	}

	@Override
	public UICommandMetadata getMetadata() {
		return Metadata.forCommand(getClass()).name("Select JPA entities").description("Scaffold using JPA entities");
	}

	@Override
	public boolean isEnabled(UIContext context) {
		return true;
	}

	@Override
	public void initializeUI(UIBuilder builder) throws Exception {
		builder.add(targetDir).add(overwrite);
	}

	@Override
	public void validate(UIValidationContext validator) {
		// TODO Auto-generated method stub

	}

	@Override
	public Result execute(UIContext context) throws Exception {
		List<Resource<?>> collectedResources = new ArrayList<Resource<?>>();
		provider.generateFrom(collectedResources, targetDir.getValue(),
				overwrite.getValue());
		return Results.success();
	}

}
