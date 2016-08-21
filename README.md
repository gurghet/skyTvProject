# SkyTv Project

A simple select page with a confirmation button

## How to use

The following assumes sbt is installed and Chrome is used.

To build the application, simply navigate this folder and run

    sbt

and then `run`.

Visit `http://localhost:9000`. You will receive an error message saying "Oops, you are not connected".
To artificially set up a cookie open the browser console and type

    document.cookie="customerID=John"

John is known for living in London. Reloading the page will show the listing relative to London.

## How to test

Running `sbt test` should be sufficient. A web driver for Chrome should be installed.