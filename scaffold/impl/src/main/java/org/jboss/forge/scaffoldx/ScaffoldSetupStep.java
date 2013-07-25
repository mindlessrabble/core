package org.jboss.forge.scaffoldx;

import org.jboss.forge.addon.ui.context.UIBuilder;
import org.jboss.forge.addon.ui.context.UIContext;
import org.jboss.forge.addon.ui.context.UIValidationContext;
import org.jboss.forge.addon.ui.metadata.UICommandMetadata;
import org.jboss.forge.addon.ui.result.NavigationResult;
import org.jboss.forge.addon.ui.result.Result;
import org.jboss.forge.addon.ui.util.Metadata;
import org.jboss.forge.addon.ui.wizard.UIWizardStep;

public class ScaffoldSetupStep implements UIWizardStep {

	@Override
	public NavigationResult next(UIContext context) throws Exception {
		return null;
	}

	@Override
	public UICommandMetadata getMetadata() {
		return Metadata.forCommand(getClass()).name("Setup")
				.description("Setup scaffold provider");
	}

	@Override
	public boolean isEnabled(UIContext context) {
		return false;
	}

	@Override
	public void initializeUI(UIBuilder builder) throws Exception {

	}

	@Override
	public void validate(UIValidationContext validator) {

	}

	@Override
	public Result execute(UIContext context) throws Exception {
		return null;
	}

}
