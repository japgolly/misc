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
