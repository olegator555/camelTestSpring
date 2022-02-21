package com.example.csv2jmscamel.routes;

import com.example.csv2jmscamel.model.Product;
import com.example.csv2jmscamel.processors.FileNameProcessor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.spi.DataFormat;
import org.springframework.stereotype.Component;

@Component
public class CsvToJmsRoute extends RouteBuilder {

    DataFormat bindy = new BindyCsvDataFormat(Product.class);

    @Override
    public void configure() throws Exception {
        from("file://{{input.folder}}?noop=true")
                .routeId("CsvFileRoute")
                .split(body().tokenize("\n")).streaming()
                .unmarshal(bindy)
                .marshal()
                .json(JsonLibrary.Gson)
                .process(new FileNameProcessor())
                .log("Unmarshalled model: ${body}")
                .to("jms:{{output.jms.queue}}");
                /*.to("activeMQ:queue:Json_converted?destinationName=${header.CamelFileName}");
                .to("file://csv_files/result/test.json?fileExist=Append&appendChars=\\n").stop();*/
    }
}
