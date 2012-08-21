# labrepl for Clojure

Copyright (c) Relevance, Inc. All rights reserved.

The use and distribution terms for this software are covered by the
[Eclipse Public License 1.0](http://opensource.org/licenses/eclipse-1.0.php)
which can be found in the file epl-v10.html at the root of this distribution.
By using this software in any fashion, you are agreeing to be bound by
the terms of this license.

You must not remove this notice, or any other, from this software.

# What is it?

Labrepl is an environment for exploring the Clojure language. It
includes:

* a web application that presents a set of lab exercises with
  step-by-step instructions
* an interactive repl for working with the lab exercises, both on the command line and in the browser
* solutions with passing tests 
* up-to-date versions of Clojure and a bunch of other libraries to explore

See the Wiki for getting started with NetBeans/Enclojure, Eclipse/Counterclockwise, Maven, Mac/Linux command line, Windows command line, IDEA/La Clojure, and Emacs.

## Getting Started

Setup instructions for most popular editors/IDEs can be found [on the wiki](https://github.com/relevance/labrepl/wiki)

When your environment is ready, you can run the web application as follows:

```
lein cljsbuild once
lein run
```

## Running the Tests

* Leiningen: `lein test`
* Maven: `mvn clojure:test`

## Thanks for contributions and reviews from

* Aaron Bedra
* Mike Clark
* Daniel Solano Gómez
* Rich Hickey
* Shawn Hoover
* Larry Karnowski
* Michael Kohl
* Jess Martin
* Alex Ott
* Laurent Petit
* Seth Schroeder
* Matthew Wizeman
