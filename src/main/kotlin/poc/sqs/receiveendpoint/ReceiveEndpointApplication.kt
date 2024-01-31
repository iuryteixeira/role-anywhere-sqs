package poc.sqs.receiveendpoint

import io.awspring.cloud.sqs.annotation.SqsListener
import io.awspring.cloud.sqs.operations.SqsTemplate
import jakarta.annotation.PostConstruct
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.time.LocalDateTime

@SpringBootApplication
class ReceiveEndpointApplication

fun main(args: Array<String>) {
    runApplication<ReceiveEndpointApplication>(*args)
}

@Component
class Receive(val sqsTemplate: SqsTemplate) {

    @PostConstruct
    fun send() {
        println("vai enviar pra fila")
        sqsTemplate.send(
            "https://sqs.us-east-1.amazonaws.com/590183968004/test-anywhere",
            ProductPrice(LocalDateTime.now().toString(), BigDecimal.valueOf(45))
        )

    }


    @SqsListener(
        value = ["https://sqs.us-east-1.amazonaws.com/590183968004/test-anywhere"],
        acknowledgementMode = "ON_SUCCESS"
    )
    fun listener(payload: ProductPrice) {
        println("chegou $payload")

    }
}

data class ProductPrice(

    val sku: String,
    val value: BigDecimal
)