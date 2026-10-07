package com.veteroch4k.factory_service.arch;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.junit.ArchTests;
import com.veteroch4k.firm.arch.FirmArchRules;

@AnalyzeClasses(
        packages = "com.veteroch4k.factory_service",
        importOptions = ImportOption.DoNotIncludeTests.class
)
public class ArchitectureTest {

    @ArchTest
    static final ArchTests rules = ArchTests.in(FirmArchRules.class);
}
