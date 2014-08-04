# Introduction

This example shows how to take the basic Dropwizard example (https://github.com/dropwizard/dropwizard/tree/master/dropwizard-example) and make it deployable to Heroku.

# Overview

This app shows how to create a full-stack Dropwizard app using Freemarker and Mustache for views, the usual Jetty/Jersey/Jackson, and a Postgres SQL backend. This documentation focuses on what was done to Heroku-ize the example, rather than the example itself, which is already documented.

# Modifications for Heroku

# Running The Application

To test the example application run the following commands.

* To package the example run.

        mvn package

* To setup the h2 database run.

        java -jar target/dropwizard-example-0.8.0-SNAPSHOT.jar db migrate example.yml

* To run the server run.

        java -jar target/dropwizard-example-0.8.0-SNAPSHOT.jar server example.yml

* To hit the Hello World example (hit refresh a few times).

	http://localhost:8080/hello-world

* To post data into the application.

	curl -H "Content-Type: application/json" -X POST -d '{"fullName":"Other Person","jobTitle":"Other Title"}' http://localhost:8080/people
	
	open http://localhost:8080/people
