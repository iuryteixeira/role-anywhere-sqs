package poc.sqs.receiveendpoint

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.awspring.cloud.sqs.operations.SqsTemplate
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.sqs.SqsAsyncClient


@Configuration
class SQSConfig {
    //
    @Bean
    fun sqsAsyncClient(): SqsAsyncClient {
        return SqsAsyncClient.builder()
            .region(Region.US_EAST_1)
            .credentialsProvider(ProfileCredentialsProvider.create("process-credential-profile"))
            .build()
    }

    @Bean
    fun sqsTemplate(): SqsTemplate = SqsTemplate
        .builder()
        .sqsAsyncClient(sqsAsyncClient())
        .configureDefaultConverter {
            it.setObjectMapper(jacksonObjectMapper())
        }.build()

}