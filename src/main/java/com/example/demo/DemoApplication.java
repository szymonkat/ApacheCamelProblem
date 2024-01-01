package com.example.demo;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.bindy.fixed.BindyFixedLengthDataFormat;
import org.apache.camel.impl.DefaultCamelContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {
    private static final Logger LOG = LoggerFactory.getLogger(DemoApplication.class);

    private static final String MESSAGE = "101 OnePlus   7Pro      0010053000     INR ";

    /**
     * "101 OnePlus 7Pro 0010053000 INR "
     * ╔════════════════╦════════╗
     * ║   Parameters   ║ Length ║
     * ╠════════════════╬════════╣
     * ║ id             ║ 4      ║
     * ║ brand          ║ 10     ║
     * ║ model          ║ 10     ║
     * ║ numberOfPieces ║ 5      ║
     * ║ price          ║ 10     ║
     * ║ currency       ║ 4      ║
     * ╚════════════════╩════════╝
     * Mobile [id=101 , brand=OnePlus , model=7Pro , numberOfPieces=100, price=53000.0, currency=INR ]
     **/
    public static void main(String[] args) {
        DemoApplication example = new DemoApplication();
        try {
            Mobile mobile = example.getMobile(MESSAGE);
            if (mobile != null) {
                LOG.info(mobile.toString());
            } else {
                LOG.error("Could not get mobile");
            }
        } catch (Exception e) {
            LOG.error("Bindy processing error: {}", e.getMessage());
        }
    }

    private Mobile getMobile(String fixedLengthMessage) throws Exception {
        Mobile mobile = null;

        try (
                BindyFixedLengthDataFormat bindy = new BindyFixedLengthDataFormat(Mobile.class);
                CamelContext camelContext = new DefaultCamelContext();
                ProducerTemplate producer = camelContext.createProducerTemplate()
        ) {

            RouteBuilder rb = new RouteBuilder() {

                @Override
                public void configure() throws Exception {
                    from("direct:start").unmarshal(bindy);
                }
            };

            camelContext.addRoutes(rb);
            camelContext.start();

            mobile = producer.requestBody("direct:start", fixedLengthMessage, Mobile.class);
        }

        return mobile;
    }
}