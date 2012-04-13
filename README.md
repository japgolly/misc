Benchmarking JSON Services
--------------------------

Nothing super-automated. Launch two terminals.

1.   Choose an interpretter: Ruby, JRuby, Java.

     **For Ruby:** `rvm use 1.9.3`

     **For JRuby:** `rvm use jruby`

     **For Java:** Install Maven and ensure it's on the path. `mvn -v`

2.   Enter the directory for the chosen interpretter. Eg. `cd ruby`

3.   If there is a `start.sh` file then you can use that to start a server. It is run like this:

     ```
     ./start.sh <app> <server>

     # Examples
     ./start.sh sinatra thin
     ./start.sh sinatra puma
     ./start.sh rails puma
     ```

     If there isn't a `start.sh` then choose a subdirectory, go into it and use the start script there.

     If you want to specify a different Java to use with JRuby, prefix your `./start.sh` command with a `JAVA_HOME=xxxx`
     Example: `JAVA_HOME=/opt/jdk1.6.0_31/ ./start.sh sinatra/ puma`

4.   Open another terminal and `cd benchmarker`

5.   Choose a config `yml` file and use it with `single.rb` to issue a single request and test the server.

     ```
     # Example
     ./single.rb api-json-ruby.yml
     ```

6.   Now call `benchmark.rb` to run the benchmark for real.

     ```
     # Example
     ./benchmark.rb api-json-ruby.yml
     ```

Benchmarking JSON Libraries
---------------------------

### Ruby

	cd json_libraries/ruby
    rvm use 1.9.3       # RVM users
	gem install bundle
    rbenv rehash        # rbenv users
    bundle install
	./benchmark.rb

### JRuby

	cd json_libraries/jruby
    rvm use jruby       # RVM users
	gem install bundle
    rbenv rehash        # rbenv users
    jruby -S bundle install
	./benchmark.sh
