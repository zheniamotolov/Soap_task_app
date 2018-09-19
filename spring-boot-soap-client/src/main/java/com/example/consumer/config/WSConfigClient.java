package com.example.consumer.config;


import com.example.consumer.client.TaskClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class WSConfigClient {
    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("com.example.consumer.wsdl");
        return marshaller;
    }

    @Bean
    public TaskClient articleClient(Jaxb2Marshaller marshaller) {
        TaskClient client = new TaskClient();
        client.setDefaultUri("http://localhost:8080/soapws/tasks.wsdl");
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }
}
