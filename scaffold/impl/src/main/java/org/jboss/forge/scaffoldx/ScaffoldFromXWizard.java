package org.jboss.forge.scaffoldx;

import javax.inject.Inject;

import org.jboss.forge.addon.scaffold.ScaffoldProvider;
import org.jboss.forge.addon.ui.context.UIBuilder;
import org.jboss.forge.addon.ui.context.UIContext;
import org.jboss.forge.addon.ui.context.UIValidationContext;
import org.jboss.forge.addon.ui.input.UISelectOne;
import org.jboss.forge.addon.ui.metadata.UICommandMetadata;
import org.jboss.forge.addon.ui.metadata.WithAttributes;
import org.jboss.forge.addon.ui.result.NavigationResult;
import org.jboss.forge.addon.ui.result.Result;
import org.jboss.forge.addon.ui.result.Results;
import org.jboss.forge.addon.ui.util.Categories;
import org.jboss.forge.addon.ui.util.Metadata;
import org.jboss.forge.addon.ui.wizard.UIWizard;

public class ScaffoldFromXWizard implements UIWizard {

	@Inject
	@WithAttributes(label = "type", required = true)
	UISelectOne<ScaffoldProvider> provider;

	@Override
	public UICommandMetadata getMetadata() {
		return Metadata.forCommand(getClass()).name("Generate scaffolding")
				.description("Generate scaffolding with provider")
				.category(Categories.create("Scaffolding", "Generation"));
	}

	@Override
	public boolean isEnabled(UIContext context) {
		return true;
	}

	@Override
	public void initializeUI(UIBuilder builder) throws Exception {
		builder.add(provider);
	}

	@Override
	public void validate(UIValidationContext validator) {

	}

	@Override
	public Result execute(UIContext context) throws Exception {
		return Results.success();
	}

	@Override
	public NavigationResult next(UIContext context) throws Exception {
		if (provider.getValue().isInstalled()) {
			return Results
					.navigateTo(provider.getValue().selectResourcesStep());
		}
		return Results.navigateTo(provider.getValue().setupStep());
	}

}