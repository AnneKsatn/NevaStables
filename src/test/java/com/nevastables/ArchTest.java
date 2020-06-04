package com.nevastables;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {

        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.nevastables");

        noClasses()
            .that()
                .resideInAnyPackage("com.nevastables.service..")
            .or()
                .resideInAnyPackage("com.nevastables.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..com.nevastables.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
