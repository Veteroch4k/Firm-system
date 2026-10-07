package com.veteroch4k.employers.arch;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.junit.ArchTests;
import com.veteroch4k.firm.arch.FirmArchRules;

@AnalyzeClasses(
        packages = "com.veteroch4k.employers",
        importOptions = ImportOption.DoNotIncludeTests.class
)
public class ArchitectureTest {

    @ArchTest
    static final ArchTests rules = ArchTests.in(FirmArchRules.class);
}
