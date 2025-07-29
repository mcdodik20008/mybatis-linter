object Vers {

    //Gradle plugins
    const val git_info = "2.4.1"
    const val dependency_analysis = "1.20.0"
    const val release_plugin = "3.0.2"
    const val rrbpm_release_plugin = "1.2.4"
    const val dokka_plugin = "1.9.20"
    const val detekt_plugin = "1.22.0"

    //Kotlin dependencies
    const val kotlin = "1.9.25"
    const val kotlinx_coroutines = "1.8.1"

    //Logging
    // https://logging.apache.org/log4j/2.x/log4j-slf4j-impl.html
    const val slf4j = "2.0.9"
    const val log4j2 = "2.21.1" //should be used with SLF4J 2.0.x releases or newer.
    const val log4j_kotlin = "1.2.0"
    const val com_lmax_disruptor = "3.4.4"
    const val log4j_http_watcher = "1.0.0"

    //Spring
    const val spring = "5.3.31"
    const val spring_boot2 = "2.7.18"
    const val spring_boot_admin = "2.7.16"
    const val spring_kafka = "2.8.7"
    const val spring_rabbitmq = "2.7.18"
    const val spring_mybatis = "2.2.2"
    const val spring_oauth2 = "2.7.18"
    const val spring_cloud_starter_openfeign = "3.1.3"
    const val spring_cloud_stream = "3.2.4"
    const val spring_cloud_config = "3.1.3"
    const val spring_cloud_function = "3.2.6"
    const val spring_security_config = "5.7.2"
    const val spring_security_oauth2_jose = "5.7.2"
    const val spring_cloud_stream_test_support = "3.2.9"

    //Codec
    const val commons_codec = "1.15"
    const val commons_lang3 = "3.14.0"

    //Apache Camel
    const val apache_camel = "3.22.3"
    const val apache_camel_kafka = "3.21.2.bft.1"
    //jackson
    const val jackson = "2.16.0"
    const val feign = "12.2"

    //JSON Utils
    const val jayway_jsonpath = "2.8.0"

    //camunda bpm
    const val camunda_bpm = "7.17.0"
    const val camunda_spin = "1.17.0"
    const val camunda_external_task_client = "7.17.0"

    //DB
    const val postgresql = "42.4.0"
    const val h2 = "2.1.214"
    const val liquibase = "4.11.0"
    const val liquibase_slf4j = "4.1.0"

    //MyBatis
    const val mybatis = "3.5.9"
    const val mybatis_spring = "2.0.7"

    //Jaxb Impl
    const val jaxb = "2.3.1"
    const val jaxb_core = "2.3.0.1"

    // Micrometer
    const val micrometer_prometheus = "1.9.1"
    const val micrometer_core = "1.11.5"

    // Jacoco
    const val jacoco_version = "0.8.7"

    //Sonar qube
    const val sonar_qube = "3.3"

    //Grpc
    const val spring_grpc = "2.13.1.RELEASE"

    //Kafka
    const val kafka_clients = "3.1.1"

    //Testing
    const val junit5 = "5.8.2"
    const val kotest = "5.3.2"
    const val mockk = "1.12.4"
    const val wiremock = "2.33.2"

    // Reactive
    const val reactor_core = "3.4.19"

    const val joda = "2.1"

    //Tika
    const val tike_core = "2.4.1"

    // OpenTelemetry
    const val opentelemetry = "1.31.0"

    const val nimbus_jose = "9.22"
    const val tomcat = "9.0.58"
}

object Plugins {

    //Gradle plugins
    const val spring_boot_plugin_id = "org.springframework.boot"
    const val kotlin_noarg_plugin_id = "org.jetbrains.kotlin.plugin.noarg"
    const val sonar_qube = "org.sonarqube"
    const val git_info = "com.gorylenko.gradle-git-properties"
    const val dependency_analysis = "com.autonomousapps.dependency-analysis"
    const val release_plugin_id = "net.researchgate.release"
    const val rrbpm_release_plugin_id = "com.bftcom.rr.rrbpm.release"
    const val dokka_plugin = "org.jetbrains.dokka"
    const val detekt_plugin = "io.gitlab.arturbosch.detekt"
}

object Libs {

    //Kotlin dependencies
    const val kotlin_bom = "org.jetbrains.kotlin:kotlin-bom:${Vers.kotlin}"
    const val kotlin_stdlib = "org.jetbrains.kotlin:kotlin-stdlib"
    const val kotlin_jdk8 = "org.jetbrains.kotlin:kotlin-stdlib-jdk8"
    const val kotlin_reflect = "org.jetbrains.kotlin:kotlin-reflect"
    const val kotlin_scripting_jsr223 = "org.jetbrains.kotlin:kotlin-scripting-jsr223"
    const val kotlin_script_runtime = "org.jetbrains.kotlin:kotlin-script-runtime"
    const val kotlinx_coroutines_bom = "org.jetbrains.kotlinx:kotlinx-coroutines-bom:${Vers.kotlinx_coroutines}"
    const val kotlinx_coroutines_core = "org.jetbrains.kotlinx:kotlinx-coroutines-core"
    const val kotlinx_coroutines_jdk8 = "org.jetbrains.kotlinx:kotlinx-coroutines-jdk8"
    const val kotlinx_coroutines_reactor = "org.jetbrains.kotlinx:kotlinx-coroutines-reactor"

    //Logging
    const val slf4j_api = "org.slf4j:slf4j-api:${Vers.slf4j}"
    const val log4j_kotlin = "org.apache.logging.log4j:log4j-api-kotlin:${Vers.log4j_kotlin}"
    const val log4j_api = "org.apache.logging.log4j:log4j-api:${Vers.log4j2}"
    const val log4j_core = "org.apache.logging.log4j:log4j-core:${Vers.log4j2}"
    const val log4j_jul = "org.apache.logging.log4j:log4j-jul:${Vers.log4j2}"
    const val log4j_layout_template_json = "org.apache.logging.log4j:log4j-layout-template-json:${Vers.log4j2}"
    const val slf4j_over_log4j = "org.apache.logging.log4j:log4j-slf4j2-impl:${Vers.log4j2}"
    const val com_lmax_disruptor = "com.lmax:disruptor:${Vers.com_lmax_disruptor}"
    const val log4j_http_watcher = "com.bftcom.log4j:http-config-monitor:${Vers.log4j_http_watcher}"

    //Spring
    const val spring_boot_dependencies = "org.springframework.boot:spring-boot-dependencies"
    const val spring_configuration_processor = "org.springframework.boot:spring-boot-configuration-processor:${Vers.spring_boot2}"
    const val spring_boot_autoconfigure = "org.springframework.boot:spring-boot-autoconfigure:${Vers.spring_boot2}"
    const val spring_boot = "org.springframework.boot:spring-boot:${Vers.spring_boot2}"
    const val spring_boot_actuator_autoconfigure = "org.springframework.boot:spring-boot-actuator-autoconfigure:${Vers.spring_boot2}"
    const val spring_boot_actuator = "org.springframework.boot:spring-boot-actuator:${Vers.spring_boot2}"
    const val spring_boot_starter = "org.springframework.boot:spring-boot-starter:${Vers.spring_boot2}"
    const val spring_boot_starter_rabbitmq = "org.springframework.boot:spring-boot-starter-amqp:${Vers.spring_rabbitmq}"
    const val spring_boot_starter_webflux = "org.springframework.boot:spring-boot-starter-webflux:${Vers.spring_boot2}"
    const val spring_kafka = "org.springframework.kafka:spring-kafka:${Vers.spring_kafka}"
    const val spring_cloud_config_starter = "org.springframework.cloud:spring-cloud-starter-config:${Vers.spring_cloud_config}"
    const val spring_cloud_stream = "org.springframework.cloud:spring-cloud-stream:${Vers.spring_cloud_stream}"
    const val spring_cloud_function_core = "org.springframework.cloud:spring-cloud-function-core:${Vers.spring_cloud_function}"
    const val spring_cloud_function_context = "org.springframework.cloud:spring-cloud-function-context:${Vers.spring_cloud_function}"
    const val spring_security_config = "org.springframework.security:spring-security-config:${Vers.spring_security_config}"
    const val spring_security_oauth2_client = "org.springframework.security:spring-security-oauth2-client:${Vers.spring_security_config}"
    const val spring_security_oauth2_core = "org.springframework.security:spring-security-oauth2-core:${Vers.spring_security_config}"
    const val spring_security_core = "org.springframework.security:spring-security-core:${Vers.spring_security_config}"
    const val spring_security_web = "org.springframework.security:spring-security-web:${Vers.spring_security_config}"
    const val spring_boot_starter_actuator = "org.springframework.boot:spring-boot-starter-actuator:${Vers.spring_boot2}"
    const val spring_cloud_starter_openfeign = "org.springframework.cloud:spring-cloud-starter-openfeign:${Vers.spring_cloud_starter_openfeign}"
    const val spring_cloud_openfeign_core = "org.springframework.cloud:spring-cloud-openfeign-core:${Vers.spring_cloud_starter_openfeign}"
    const val spring_boot_starter_mybatis = "org.mybatis.spring.boot:mybatis-spring-boot-starter:${Vers.spring_mybatis}"
    const val spring_context = "org.springframework:spring-context:${Vers.spring}"
    const val spring_jdbc = "org.springframework:spring-jdbc:${Vers.spring}"
    const val spring_integration_core = "org.springframework.integration:spring-integration-core:5.5.13"
    const val spring_integration_jmx = "org.springframework.integration:spring-integration-jmx:5.5.13"
    const val spring_tx = "org.springframework:spring-tx:${Vers.spring}"
    const val spring_aop = "org.springframework:spring-aop:${Vers.spring}"
    const val spring_messaging = "org.springframework:spring-messaging:${Vers.spring}"
    const val spring_oauth2_client = "org.springframework.boot:spring-boot-starter-oauth2-client:${Vers.spring_oauth2}"
    const val spring_boot_starter_aop = "org.springframework.boot:spring-boot-starter-aop:${Vers.spring_boot2}"
    const val spring_web = "org.springframework:spring-web:${Vers.spring}"
    const val spring_beans = "org.springframework:spring-beans:${Vers.spring}"
    const val spring_core = "org.springframework:spring-core:${Vers.spring}"
    const val spring_security_oauth2_jose = "org.springframework.security:spring-security-oauth2-jose:${Vers.spring_security_oauth2_jose}"
    const val spring_security_oauth2_resource_server = "org.springframework.security:spring-security-oauth2-resource-server:${Vers.spring_security_config}"
    const val spring_cloud_stream_test_support = "org.springframework.cloud:spring-cloud-stream-test-support:${Vers.spring_cloud_stream_test_support}"
    const val spring_boot_test = "org.springframework.boot:spring-boot-test:${Vers.spring_boot2}"
    const val spring_test = "org.springframework:spring-test:${Vers.spring}"
    const val spring_boot_test_autoconfigure = "org.springframework.boot:spring-boot-test-autoconfigure:${Vers.spring_boot2}"
    const val spring_boot_admin_starter_client = "de.codecentric:spring-boot-admin-starter-client:${Vers.spring_boot_admin}"
    const val spring_retry = "org.springframework.retry:spring-retry:1.3.4"
    const val spring_expression = "org.springframework:spring-expression:${Vers.spring}"

    //Codec
    const val commons_codec = "commons-codec:commons-codec:${Vers.commons_codec}"
    const val commons_lang3 = "org.apache.commons:commons-lang3:${Vers.commons_lang3}"

    //Apache Camel
    const val camel_kafka_starter = "org.apache.camel.springboot:camel-kafka-starter:${Vers.apache_camel}"
    const val camel_kafka = "org.apache.camel:camel-kafka:${Vers.apache_camel_kafka}"
    const val camel_sprint_rabbit_starter = "org.apache.camel.springboot:camel-spring-rabbitmq-starter:${Vers.apache_camel}"
    const val camel_api = "org.apache.camel:camel-api:${Vers.apache_camel}"
    const val camel_core_model = "org.apache.camel:camel-core-model:${Vers.apache_camel}"
    const val camel_jackson = "org.apache.camel:camel-jackson:${Vers.apache_camel}"
    const val camel_direct = "org.apache.camel:camel-direct:${Vers.apache_camel}"
    const val camel_scheduler = "org.apache.camel.springboot:camel-scheduler-starter:${Vers.apache_camel}"
    const val camel_test_spring_junit5 = "org.apache.camel:camel-test-spring-junit5:${Vers.apache_camel}"
    const val camel_spring_boot = "org.apache.camel.springboot:camel-spring-boot:${Vers.apache_camel}"
    const val camel_support = "org.apache.camel:camel-support:${Vers.apache_camel}"
    const val camel_micrometer = "org.apache.camel:camel-micrometer:${Vers.apache_camel}"
    const val camel_spring_rabbitmq = "org.apache.camel:camel-spring-rabbitmq:${Vers.apache_camel}"
    const val amqp_client = "com.rabbitmq:amqp-client:5.14.2"
    const val camel_management = "org.apache.camel:camel-management:${Vers.apache_camel}"

    //Jackson
    const val jackson_kotlin_module = "com.fasterxml.jackson.module:jackson-module-kotlin:${Vers.jackson}"
    const val jackson_jsr310 = "com.fasterxml.jackson.datatype:jackson-datatype-jsr310:${Vers.jackson}"
    const val jackson_annotations = "com.fasterxml.jackson.core:jackson-annotations:${Vers.jackson}"
    const val jackson_dataformat_yaml = "com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:${Vers.jackson}"
    const val jackson_databind = "com.fasterxml.jackson.core:jackson-databind:${Vers.jackson}"
    const val jackson_datatype_jdk8 = "com.fasterxml.jackson.datatype:jackson-datatype-jdk8:${Vers.jackson}"
    const val jackson_module_parameter_names = "com.fasterxml.jackson.module:jackson-module-parameter-names:${Vers.jackson}"
    const val feign_jackson = "io.github.openfeign:feign-jackson:${Vers.feign}"
    const val feign_micrometer = "io.github.openfeign:feign-micrometer:${Vers.feign}"

    // JSON Utils
    const val jayway_jsonpath = "com.jayway.jsonpath:json-path:${Vers.jayway_jsonpath}"

    //camunda bpm
    const val camunda_bom = "org.camunda.bpm:camunda-bom:${Vers.camunda_bpm}"
    const val camunda_engine = "org.camunda.bpm:camunda-engine:${Vers.camunda_bpm}"
    const val camunda_engine_plugin_spin = "org.camunda.bpm:camunda-engine-plugin-spin:${Vers.camunda_bpm}"
    const val camunda_spin_dataformat_all = "org.camunda.spin:camunda-spin-dataformat-all"
    const val camunda_engine_rest_jaxrs2 = "org.camunda.bpm:camunda-engine-rest-jaxrs2:${Vers.camunda_bpm}"
    const val camunda_bpm_spring_boot_starter = "org.camunda.bpm.springboot:camunda-bpm-spring-boot-starter:${Vers.camunda_bpm}"
    const val camunda_bpm_spring_boot_starter_rest = "org.camunda.bpm.springboot:camunda-bpm-spring-boot-starter-rest:${Vers.camunda_bpm}"
    const val camunda_bpm_spring_boot_starter_webapp = "org.camunda.bpm.springboot:camunda-bpm-spring-boot-starter-webapp:${Vers.camunda_bpm}"
    const val camunda_bpm_spring_boot_bom = "$spring_boot_dependencies:${Vers.spring_boot2}"
    const val camunda_bpm_spring_boot_starter_jdbc = "org.springframework.boot:spring-boot-starter-jdbc:${Vers.spring_boot2}"
    const val camunda_bpm_spring_boot_starter_validation = "org.springframework.boot:spring-boot-starter-validation:${Vers.spring_boot2}"
    const val camunda_bpm_spring_boot_starter_jersey = "org.springframework.boot:spring-boot-starter-jersey:${Vers.spring_boot2}"
    const val camunda_bpm_spring_boot_starter_actuator = "org.springframework.boot:spring-boot-starter-actuator:${Vers.spring_boot2}"
    const val camunda_spin_dataformat_json_jackson = "org.camunda.spin:camunda-spin-dataformat-json-jackson:${Vers.camunda_spin}"
    const val camunda_spin_core = "org.camunda.spin:camunda-spin-core:${Vers.camunda_spin}"
    const val camunda_commons_typed_values = "org.camunda.commons:camunda-commons-typed-values:${Vers.camunda_external_task_client}"
    const val camunda_engine_rest_core = "org.camunda.bpm:camunda-engine-rest-core:${Vers.camunda_bpm}"

    //DB
    const val h2 = "com.h2database:h2:${Vers.h2}"
    const val postgresql = "org.postgresql:postgresql:${Vers.postgresql}"
    const val liquibase_core = "org.liquibase:liquibase-core:${Vers.liquibase}"
    const val liquibase_slf4j = "com.mattbertolini:liquibase-slf4j:${Vers.liquibase_slf4j}"
    const val hikaricp = "com.zaxxer:HikariCP"

    //MyBatis
    const val mybatis = "org.mybatis:mybatis:${Vers.mybatis}"
    const val mybatis_spring = "org.mybatis:mybatis-spring:${Vers.mybatis_spring}"

    //Jaxb impl
    const val jaxb_api = "javax.xml.bind:jaxb-api:${Vers.jaxb}"
    const val jaxb_impl = "com.sun.xml.bind:jaxb-impl:${Vers.jaxb}"
    const val jaxb_core = "com.sun.xml.bind:jaxb-core:${Vers.jaxb_core}"
    const val johnzon_core = "org.apache.johnzon:johnzon-core:1.2.18"
    const val snakeyaml = "org.yaml:snakeyaml:2.2"

    // Micrometer
    const val micrometer_prometheus = "io.micrometer:micrometer-registry-prometheus:${Vers.micrometer_prometheus}"
    const val micrometer_core = "io.micrometer:micrometer-core:${Vers.micrometer_core}"

    // Reactor
    const val reactor_core = "io.projectreactor:reactor-core:${Vers.reactor_core}"

    //lombock
    const val lombok = "org.projectlombok:lombok:1.18.30"

    //BFT
    const val bftcom_rr_internal_bom = "com.bftcom.rr.bpm:rrbpm-internal-bom-rc-fo1:" // номер версии получим из gradle.properties
    const val bftcom_rr_internal_bom_version = "com.bftcom.rr.bpm:rrbpm-internal-bom-version"
    const val bftcom_phd_web_client = "com.bftcom.rr:rrbpm-phd-web-client"
    const val supercode_phd_client_models = "ru.supercode.rr.phd:phd-graphql-client"
    const val supercode_phd_client_base = "ru.supercode.rr.phd:phd-graphql-client-base"
    const val bftcom_rr_database_config = "com.bftcom.rr.shared:rrbpm-database-config"
    const val bftcom_rr_database_test_container = "com.bftcom.rr.shared:rrbpm-database-test-container"
    const val bft_unsi_web_client = "com.bftcom.rr.bpm.integration:rrbpm-unsi-web-client"
    const val bft_sia_proxy_model = "com.bftcom.rr:sia-proxy-model"
    const val temp_numbers_web_client = "com.bftcom.rr.bpm:rrbpm-record-temp-numbers-web-client"
    const val temp_numbers_model = "com.bftcom.rr.bpm:rrbpm-record-temp-numbers-models"
    const val business_calendar = "com.bftcom.rr.shared:rrbpm-business-calendar"
    const val supercode_audit_client = "ru.supercode.rr.audit:rr-audit-client"
    const val bftcom_system_notifications_api = "com.bftcom.rr:rrbpm-system-notifications-api"
    const val bftcom_system_notifications_client = "com.bftcom.rr:rrbpm-system-notifications-client"
    const val bftcom_rr_common_tracing = "com.bftcom.rr.shared:rrbpm-common-tracing"
    const val bftcom_rr_camel_tracing = "com.bftcom.rr.shared:rrbpm-camel-tracing"
    const val bftcom_rr_shared_oauth2_security_config = "com.bftcom.rr.bpm.shared.spring-config:rrbpm-oauth2-security-config"
    const val bftcom_rr_shared_cloud_config = "com.bftcom.rr.shared.spring-config:rrbpm-cloud-config"
    const val bftcom_rr_shared_metric_config = "com.bftcom.rr.shared.spring-config:rrbpm-metric-config"
    const val bftcom_rr_shared_utils = "com.bftcom.rr.shared:rrbpm-utils"
    const val bftcom_rr_dlq_error_handler = "com.bftcom.rr.dlq-error-handler:rrbpm-dlq-error-handler-adapter"
    const val bftcom_rr_shared_kafka_config = "com.bftcom.rr.shared.spring-config:rrbpm-kafka-config"
    const val bftcom_rr_shared_logical_transactions = "com.bftcom.rr.shared.logical:rrbpm-logical-transactions"
    const val bftcom_rr_shared_rrbpm_database = "com.bftcom.rr.shared:rrbpm-database"
    const val bftcom_rr_verification_service_client = "com.bftcom.rr.bpm.verification:rrbpm-verification-service-client"

    const val bftcom_rr_shared_dictionaries_set_of_stages = "com.bftcom.rr.bpm.shared.dictionaries:rrbpm-set-of-stages"
    const val bftcom_rr_shared_dictionaries_origin_reason = "com.bftcom.rr.bpm.shared.dictionaries:rrbpm-origin-reason"
    const val bftcom_rr_shared_dictionaries_extinguishment_reason = "com.bftcom.rr.bpm.shared.dictionaries:rrbpm-extinguishment-reason"
    const val bftcom_rr_shared_dictionaries_link_type = "com.bftcom.rr.bpm.shared.dictionaries:rrbpm-link-type"
    const val bftcom_rr_shared_dictionaries_request_status = "com.bftcom.rr.bpm.shared.dictionaries:rrbpm-request-status"
    const val bftcom_rr_shared_dictionaries_terms_of_services = "com.bftcom.rr.bpm.shared.dictionaries:rrbpm-terms-of-services"
    const val bftcom_rr_shared_dictionaries_pcr_action_code = "com.bftcom.rr.bpm.shared.dictionaries:rrbpm-pcr-action-code"
    const val bftcom_rr_shared_dictionaries_request_decisions = "com.bftcom.rr.bpm.shared.dictionaries:rrbpm-request-decisions"
    const val bftcom_rr_shared_dictionaries_right_number_map = "com.bftcom.rr.bpm.shared.dictionaries:rrbpm-right-number-map"
    const val bftcom_rr_shared_dictionaries_payment_statuses = "com.bftcom.rr.bpm.shared.dictionaries:rrbpm-payment-statuses"
    const val bftcom_rr_shared_dictionaries_signed_objects_by_form_key =
        "com.bftcom.rr.bpm.shared.dictionaries:rrbpm-signed-objects-by-form-key"
    const val bftcom_rr_shared_dictionaries_opf_code_unsi = "com.bftcom.rr.bpm.shared.dictionaries:rrbpm-opf-code-unsi"

    const val bftcom_rr_models_camunda_web_openapi_model = "com.bftcom.rr.bpm:rrbpm-camunda-web-openapi-model"
    const val bftcom_rr_models_shared_models = "com.bftcom.rr.bpm.models:rrbpm-shared-model"
    const val bftcom_rr_models_common_model = "com.bftcom.rr.bpm.models:rrbpm-common-model"
    const val bftcom_rr_models_command_handler_api = "com.bftcom.rr.bpm.models:rrbpm-command-handler-api"
    const val bftcom_rr_models_ups_api_model = "com.bftcom.rr.bpm.models:rrbpm-ups-api-model"
    const val bftcom_rr_models_uds_api_model = "com.bftcom.rr.bpm.models:rrbpm-uds-api-model"

    const val bftcom_rr_integration_camunda_web_client = "com.bftcom.rr.bpm.integration:rrbpm-camunda-web-client"
    const val bftcom_rr_integration_smb_web_client = "com.bftcom.rr.bpm.integration:rrbpm-smb-web-client"
    const val bftcom_rr_integration_audit_client = "com.bftcom.rr.bpm.integration:rrbpm-audit-client"
    const val bftcom_rr_integration_external_task_client = "com.bftcom.rr.bpm.integration:rrbpm-external-task-client"

    const val bftcom_rr_services_api = "com.bftcom.rr.shared.services:rrbpm-services-api"
    const val bftcom_rr_services_common_services = "com.bftcom.rr.shared.services:rrbpm-common-services"

    const val bftcom_rr_document_api_model = "com.bftcom.rr.bpm.document:document-api-model"

    const val bftcom_rr_sign_orchestrator_messaging_model = "com.bftcom.rr.bpm.sign:rrbpm-messaging-model"


    //Grpc
    const val spring_grpc_server = "net.devh:grpc-server-spring-boot-starter:${Vers.spring_grpc}"

    // JodaTime
    const val joda_time = "joda-time:joda-time:${Vers.joda}"

    //Kafka
    const val kafka_clients = "org.apache.kafka:kafka-clients:${Vers.kafka_clients}"

    //Testing
    //JUnit5
    const val junit5_api = "org.junit.jupiter:junit-jupiter-api:${Vers.junit5}"
    const val junit5_params = "org.junit.jupiter:junit-jupiter-params:${Vers.junit5}"
    const val junit5_engine = "org.junit.jupiter:junit-jupiter-engine:${Vers.junit5}"
    //Kotest
    const val kotest_assertions_core = "io.kotest:kotest-assertions-core:${Vers.kotest}"
    const val kotest_assertions_shared = "io.kotest:kotest-assertions-shared:${Vers.kotest}"
    const val kotest_assertions_json = "io.kotest:kotest-assertions-json-jvm:${Vers.kotest}"
    //Mockk
    const val mockk = "io.mockk:mockk:${Vers.mockk}"
    const val mockk_dsl_jvm = "io.mockk:mockk-dsl-jvm:${Vers.mockk}"
    const val wiremock = "com.github.tomakehurst:wiremock-jre8:${Vers.wiremock}"
    //Spring
    const val spring_boot_starter_test = "org.springframework.boot:spring-boot-starter-test:${Vers.spring_boot2}"

    //Camel
    const val camel_bean = "org.apache.camel:camel-bean:${Vers.apache_camel}"
    const val camel_core_languages = "org.apache.camel:camel-core-languages:${Vers.apache_camel}"

    //Tika
    const val tika_core = "org.apache.tika:tika-core:${Vers.tike_core}"

    // OpenTelemetry
    const val opentelemetry_instrumentation_annotations = "io.opentelemetry.instrumentation:opentelemetry-instrumentation-annotations:${Vers.opentelemetry}"

    const val nimbus_jose = "com.nimbusds:nimbus-jose-jwt:${Vers.nimbus_jose}"
    const val tomcat_embed_core = "org.apache.tomcat.embed:tomcat-embed-core:${Vers.tomcat}"
}

object Repos {
    const val rosreestr_central = "https://nexus.supercode.ru/repository/rosreestr-public/"
}

const val ProjectGroup = "com.bftcom.rr.bpm"
