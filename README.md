# Introduction

This example shows how to take the basic [Dropwizard example][1]  and make it deployable to Heroku.

# Overview

This app shows how to create a full-stack Dropwizard app using Freemarker and Mustache for views, the usual Jetty/Jersey/Jackson, and a Postgres SQL backend. This documentation focuses on what was done to Heroku-ize the example, rather than the example itself, which is already documented.

# Modifications for Heroku

A few changes were necessary to make the app deployable to Heroku
* Add a Procfile
  * This tells Heroku (Foreman, specifically) how to start your app. Here, that means running database migrations and then starting our app with the Heroku JVM args.
* Use 'simple' (single-port) server
  * Dropwizard typically uses two ports: one for the application and one for admin; Heroku only supports exposing a single port.
* Get configuration from the Heroku environment rather than Dropwizard's example.yml
  * Heroku supplies the DB config via the DATABASE_URL environment variable, which provides a secure way for us to get the DB connection settings.
* Minor database schema changes
  * Fix a couple problems (maybe due to using postgres) with ID generation and camel-case column names.

# Running the App Locally
You can now run the example with the following commands.

* Build the example

        mvn package

* Start the example using foreman (get from installing the [Heroku toolbelt][2]. This will start your project using the command in the Procfile.

        foreman start
	
* Hit the example in your browser

	`http://localhost:5000/hello-world`
	
* Post data into the application

        curl -H "Content-Type: application/json" -X POST -d '{"fullName":"Other Person","jobTitle":"Other Title"}' http://localhost:8080/people
	 
* See the data you posted

	`http://localhost:8080/people`

# Deploying to Heroku

First, install the [Heroku toolbelt][2]. This will give you what you need to get started.
- Foreman for running your app
- Git
- Heroku client

Create a Heroku account if you haven't already. Then navigate to the project directiory you cloned from github.

Clone the project and cd into it

        git clone https://github.com/alexroussos/dropwizard-heroku-example.git

Login to Heroku

        heroku login

Now create a project in Heroku

        heroku create <project name>
	
Create a database in Heroku. You don't need to provide any config in your project; it comes from the $DATABASE_URL in Heroku.
        
        heroku addons:add heroku-postgresql:dev
	
Creating the project in Heroku added a git remote, which you can now push to.

        git push heroku master

Pushing to git will also deploy your project. You can now open it.

        heroku  open
	
If that fails, see the logs

        heroku logs

If you need more information, see the [Heroku setup][3].

	
[1]: https://github.com/dropwizard/dropwizard/tree/master/dropwizard-example
[2]: https://toolbelt.heroku.com/
[3]: https://devcenter.heroku.com/articles/quickstart
