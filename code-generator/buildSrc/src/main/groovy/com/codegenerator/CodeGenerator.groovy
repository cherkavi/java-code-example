package com.codegenerator;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

class BrandServerCodeGenerator implements Plugin<Project> {
    void apply(Project project) {
        project.task('generate-code', type: DomainCodeGenerator)
    }
}