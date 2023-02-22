import ballerina/http;
import ballerina/io;
@http:ServiceConfig {
    basePath: "/hello"
}
service<http:Service> helloApi bind { port: 9090 };
@http:ResourceConfig {
    methods: ["GET"],
    path: "/"
}
resource sayHello (http:Caller caller, http:Request req) {
    http:Response res = new;
    res.setTextPayload("Hello, World!");
    _ = caller->respond(res);
}
