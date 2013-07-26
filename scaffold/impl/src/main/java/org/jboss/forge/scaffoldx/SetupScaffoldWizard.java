package org.jboss.forge.scaffoldx;

import javax.inject.Inject;

import org.jboss.forge.addon.resource.DirectoryResource;
import org.jboss.forge.addon.scaffold.ScaffoldProvider;
import org.jboss.forge.addon.ui.UICommand;
import org.jboss.forge.addon.ui.context.UIBuilder;
import org.jboss.forge.addon.ui.context.UIContext;
import org.jboss.forge.addon.ui.context.UIValidationContext;
import org.jboss.forge.addon.ui.input.UIInput;
import org.jboss.forge.addon.ui.input.UISelectOne;
import org.jboss.forge.addon.ui.metadata.UICommandMetadata;
import org.jboss.forge.addon.ui.metadata.WithAttributes;
import org.jboss.forge.addon.ui.result.Result;
import org.jboss.forge.addon.ui.result.Results;
import org.jboss.forge.addon.ui.util.Categories;
import org.jboss.forge.addon.ui.util.Metadata;

public class SetupScaffoldWizard implements UICommand {

	@Inject
	@WithAttributes(label = "Scaffold provider")
	UISelectOne<ScaffoldProvider> provider;

	@Inject
	@WithAttributes(label = "Target directory")
	UIInput<DirectoryResource> targetDir;

	@Inject
	@WithAttributes(label = "Overwrite")
	UIInput<Boolean> overwrite;

	@Inject
	@WithAttributes(label = "Install templates")
	UIInput<Boolean> installTemplates;

	@Override
	public UICommandMetadata getMetadata() {
		return Metadata.forCommand(getClass()).name("Setup scaffolding")
				.description("Setup scaffolding in the project")
				.category(Categories.create("Scaffolding", "Generation"));
	}

	@Override
	public boolean isEnabled(UIContext context) {
		return true;
	}

	@Override
	public void initializeUI(UIBuilder builder) throws Exception {
		builder.add(provider).add(overwrite);
	}

	@Override
	public void validate(UIValidationContext validator) {

	}

	@Override
	public Result execute(UIContext context) throws Exception {
		ScaffoldProvider scaffoldProvider = provider.getValue();
		scaffoldProvider.setup(targetDir.getValue(), overwrite.getValue(),
				installTemplates.getValue());
		return Results.success("Scaffold " + scaffoldProvider.getName()
				+ " has been setup.");
	}

}
