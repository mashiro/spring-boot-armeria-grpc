package com.example.demo

import com.linecorp.armeria.common.grpc.GrpcSerializationFormats
import com.linecorp.armeria.server.grpc.GrpcService
import com.linecorp.armeria.server.healthcheck.HealthChecker
import com.linecorp.armeria.server.tomcat.TomcatService
import com.linecorp.armeria.spring.ArmeriaServerConfigurator
import io.grpc.BindableService
import io.grpc.protobuf.services.ProtoReflectionService
import org.apache.catalina.connector.Connector
import org.springframework.boot.web.embedded.tomcat.TomcatWebServer
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ArmeriaConfig {
    companion object {
        fun getConnector(applicationContext: ServletWebServerApplicationContext): Connector {
            val container = applicationContext.webServer as TomcatWebServer
            container.start()
            return container.tomcat.connector
        }
    }

    @Bean
    fun tomcatConnectorHealthChecker(applicationContext: ServletWebServerApplicationContext): HealthChecker {
        val connector = getConnector(applicationContext)
        return HealthChecker { connector.state.isAvailable }
    }

    @Bean
    fun tomcatService(applicationContext: ServletWebServerApplicationContext): TomcatService {
        return TomcatService.of(getConnector(applicationContext))
    }

    @Bean
    fun armeriaServiceInitializer(
        tomcatService: TomcatService,
        grpcServices: List<BindableService>
    ): ArmeriaServerConfigurator {
        return ArmeriaServerConfigurator { builder ->
            builder.service("prefix:/", tomcatService)
            builder.service(
                GrpcService
                    .builder()
                    .addService(ProtoReflectionService.newInstance())
                    .addServices(grpcServices)
                    .supportedSerializationFormats(GrpcSerializationFormats.values())
                    .enableUnframedRequests(true)
                    .useBlockingTaskExecutor(true)
                    .build()
            )
        }
    }
}
