package com.example.csv2jmscamel;

import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.NotifyBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;


@EnableAutoConfiguration
@SpringBootTest(properties = "camel.springboot.name=MyName")
class Csv2jmsCamelApplicationTests {

    @Autowired
    ProducerTemplate producerTemplate;
    @Autowired
    CamelContext camelContext;
    @EndpointInject("mock:jms")
    MockEndpoint mockEndpoint;


    @Test
    void wasContextAutowired() {
        assert(camelContext!=null);
    }
    @Test
    void wasProducerAutowired() {
        assert(producerTemplate!=null);
    }
    @Test
    void receiveTest() {
        NotifyBuilder notifyBuilder = new NotifyBuilder(camelContext)
                .from("direct:file")
                .whenDone(3)
                .whenReceivedSatisfied(mockEndpoint)
                .create();
    }

}
