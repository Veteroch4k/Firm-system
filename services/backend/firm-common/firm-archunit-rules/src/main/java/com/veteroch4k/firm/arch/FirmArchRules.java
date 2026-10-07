package com.veteroch4k.firm.arch;

import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

public class FirmArchRules  {

	@ArchTest
	public static final ArchRule layeredArchitecture = layeredArchitecture()
			.consideringOnlyDependenciesInLayers()
			.layer("Controller").definedBy("..controller..")
			.layer("Service").definedBy("..service..")
			.layer("Repository").definedBy("..repository..")
			.whereLayer("Controller").mayNotBeAccessedByAnyLayer()
			.whereLayer("Service").mayOnlyBeAccessedByLayers("Controller")
			.whereLayer("Repository").mayOnlyBeAccessedByLayers("Service");



}
