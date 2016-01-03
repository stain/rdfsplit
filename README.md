# rdfsplit

Split large RDF files into reasonably sized N-Quads files

## Docker

This tool is also available as the [Docker](https://www.docker.com/) image
[stain/rdfsplit](https://hub.docker.com/r/stain/rdfsplit/).

You should mount your RDF directory as the volume `/data`
which will be the current directory within the Docker image.
Note that for the relative paths of your shell expansion (e.g. `*.ttl`)
to work, you should be in the exported directory when running docker.

To use:

```
docker run stain/rdfsplit rdfsplit --help
cd /home/johndoe/rdfstuff
mkdir split
docker run stain/rdfsplit -v /home/johndoe/rdfstuff:/data rdfsplit --output split/ *.ttl
```

## Installation

First ensure you have Java 8 (or higher) and [Leiningen](http://leiningen.org/), then:

    lein uberjar

You will find the executable in the equivalent of
`target/uberjar/rdfsplit-0.1.0-SNAPSHOT-standalone.jar`

## Usage

    $ java -jar rdfsplit-0.1.0-standalone.jar --help

## Options

Options:

```
  -o, --output OUTPUTDIR  .  Output directory (default is current directory)
  -r, --recursive            Recurse into subdirectories
  -f, --force                Overwrite any existing output files. Make output directory if missing.
  -v, --verbose              Verbose log output
  -h, --help
```

## Examples

```
java -jar rdfsplit-0.1.0-standalone.jar --output split/ *.ttl
```

### Contribute

Please feel free to [contribute](https://github.com/stain/rdfsplit/pulls)
and report any [issues](https://github.com/stain/rdfsplit/issues)
at the [rdfsplit Github repository](https://github.com/stain/rdfsplit/).


## License

Copyright © 2015-2016 [Stian Soiland-Reyes](http://orcid.org/0000-0001-9842-9718)

Distributed under [Apache License, version 2.0](http://www.apache.org/licenses/LICENSE-2.0).

Dependencies included in the produced standalone jar includes
[Apache Jena](http://jena.apache.org/) (Apache license) and
[Clojure](http://clojure.org/) (Eclipse license).
