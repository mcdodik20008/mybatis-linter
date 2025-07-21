plugins {
    kotlin("jvm")
    kotlin("kapt")
    jacoco
}

jacoco {
    toolVersion = Vers.jacoco_version
}

tasks.test {
    finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
    reports {
        xml.required.set(true)
    }
    dependsOn(tasks.test)
}

dependencies {
    kapt(Libs.spring_configuration_processor)
    annotationProcessor(Libs.spring_configuration_processor)

    //kotlin
    implementation(platform(Libs.kotlin_bom))
    implementation(Libs.kotlin_stdlib)
    implementation(Libs.kotlin_jdk8)
    runtimeOnly(Libs.kotlin_reflect)
    implementation(platform(Libs.kotlinx_coroutines_bom))
    implementation(Libs.kotlinx_coroutines_core)
    implementation(Libs.kotlinx_coroutines_reactor)

    //spring
    implementation(Libs.spring_context)
    compileOnly(Libs.spring_boot_autoconfigure)
    implementation(Libs.spring_oauth2_client)
    implementation(Libs.spring_boot)
    implementation(Libs.spring_boot_actuator)
    implementation(Libs.spring_security_config)
    implementation(Libs.spring_security_oauth2_client)
    implementation(Libs.spring_security_oauth2_core)
    implementation(Libs.spring_beans)

    //spring actuator
    implementation(Libs.spring_boot_starter_actuator)
    implementation(Libs.spring_boot_actuator_autoconfigure)

    //prometheus
    implementation(Libs.micrometer_core)

    implementation(Libs.ben_manes_caffeine)

    implementation(Libs.opentelemetry_api)

    //BFT
    implementation(Libs.bftcom_rr_common_tracing)
    implementation(Libs.bftcom_rr_shared_oauth2_security_config)
    implementation(Libs.bftcom_rr_shared_utils)
    implementation(Libs.bftcom_rr_models_camunda_web_openapi_model)
    implementation(Libs.bftcom_rr_integration_camunda_web_client)
    implementation(Libs.bftcom_rr_models_shared_models)
    implementation(Libs.bftcom_rr_integration_audit_client)
    implementation(Libs.bftcom_rr_services_api)

    //logging
    runtimeOnly(Libs.log4j_api)
    implementation(Libs.log4j_kotlin)
    runtimeOnly(Libs.slf4j_over_log4j)

    implementation(Libs.reactor_core)
    implementation(Libs.httpcomponents_core)
    implementation(Libs.jackson_databind)

    //Testing
    testImplementation(Libs.junit5_api)
    testRuntimeOnly(Libs.junit5_engine)
    testImplementation(Libs.camunda_commons_typed_values)
    testImplementation(Libs.spring_tx)

    //Kotest
    testImplementation(Libs.kotest_assertions_api)
    testImplementation(Libs.kotest_assertions_shared)
    testImplementation(Libs.kotest_assertions_core)

    //Test logging
    testRuntimeOnly(Libs.log4j_core)
    testRuntimeOnly(Libs.slf4j_over_log4j)

    //Mocking
    testImplementation(Libs.mockk)
    testImplementation(Libs.mockk_dsl_jvm)
}
