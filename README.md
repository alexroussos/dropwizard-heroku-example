# Introduction

This example shows how to take the basic [Dropwizard example][1]  and make it deployable to Heroku.

# Overview

This app shows how to create a full-stack Dropwizard app using Freemarker and Mustache for views, the usual Jetty/Jersey/Jackson, and a Postgres SQL backend. This documentation focuses on what was done to Heroku-ize the example, rather than the example itself, which is already documented.

# Modifications for Heroku

A few changes were necessary to make the app deployable to Heroku
* Add a Procfile
* Use 'simple' (single-port) server
* Get configuration from the Heroku environment rather than Dropwizard's example.yml
* Minor database schema changes

# Running The Application

First, install the [Heroku toolbelt][2]. This will give you what you need to get started.
- Foreman for running your app
- Git
- Heroku client


heroku create
git push heroku master

You'll then need to follow t [Heroku setup][3]

You can now run the example with the following commands.

* Build the example
        mvn package
        
* Start the example using foreman
	foreman start

* To setup the h2 database run.

        java -jar target/dropwizard-example-0.8.0-SNAPSHOT.jar db migrate example.yml

* To run the server run.

        java -jar target/dropwizard-example-0.8.0-SNAPSHOT.jar server example.yml

* To hit the Hello World example (hit refresh a few times):

	http://localhost:5000/hello-world

* To post data into the application:

	curl -H "Content-Type: application/json" -X POST -d '{"fullName":"Other Person","jobTitle":"Other Title"}' http://localhost:8080/people
	
	open http://localhost:8080/people
	
[1]: https://github.com/dropwizard/dropwizard/tree/master/dropwizard-example
[2]: https://toolbelt.heroku.com/
[3]: https://devcenter.heroku.com/articles/quickstart
