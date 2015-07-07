# AsyncJavaMicroservice
Asynchronous Java based Microservice Prototype

## Description

This project allows to fastly create a basic micorservice based on asynchronous processes. 

Acting simultaneously as a server and REST client the prototype includes several ways to make async calls to other remote resources. 

By using CompletableFuture the Prototype shows how to spawn simultaneous async threads and merging the result in a single response. 

The Prototype is extensively based on Jersey and the Jersey Rx Library.

Please read a motivations at http://itpieceofadvice.blogspot.ie/2015/06/lightNIOJavamicroservice.html

## Web Server

The web server is Grizzly. Completely async managed requests. 

No more sync Tomcat for us.

## Stop Spring!

If you want your apps are slow and heavy, it´s up to you. 

This Prototype is 100% Spring free for several reason (read the blog link).

## Microservice

The artifact is an uber jar file from the Maven Shade Plugin.

## REST Clients

The included types are:

Plain Asynchronous client Reactive based client Observable client

In order to easily comare times a synchronous client has been included.

## REST Servers

All services handle asynchronously the requests, returning aggregation of Futures and Observable.

## Features

Jersey based Integration Test Exception handling Performance log.

Ideal for dashboarding your services.

Identity Service Performance Service with metrics.
