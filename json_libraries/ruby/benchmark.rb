#!/usr/bin/env ruby
require 'rubygems'
require 'bundler'
Bundler.require :default
require 'benchmark'

#-------------------------------------------------------------------------------

ENGINES= if ARGV.empty?
           (MultiJson::REQUIREMENT_MAP.map{|e| e[1]} + [:ok_json]).uniq
         else
           ARGV.map(&:to_sym)
         end
REPS= (ENV['REPS'] || '100000').to_i
WARMUPS= (ENV['WARMUPS'] || '1').to_i

DATA= {
  a: 2,
  b: (1..50).to_a,
  c: %w[asf xcvb sdfg sdf gfsd],
  d: {
    omg: 'hedfasgdsfg',
    wewr: 34,
    sfgjbsdf: %w[sdfg sdfgsdfgnj klj kj hkuih ui hu kjb bkj b sdfg],
  },
}

#-------------------------------------------------------------------------------

def test_writing(engine)
  MultiJson.use engine
  for i in 1..REPS; MultiJson.dump DATA end
end

def test_reading(engine)
  MultiJson.use engine
  data= MultiJson.dump(DATA).freeze
  for i in 1..REPS; MultiJson.load data end
end

def benchmark(name, method, sets=1)
  puts name
  Benchmark.bm(20) do |x|
    sets.times do
      ENGINES.each do |e|
        x.report("#{e}:") { send method, e }
      end
    end
  end
  puts
end

#-------------------------------------------------------------------------------

puts "#{RUBY_DESCRIPTION}\n\n"
puts "ENGINES: #{ENGINES}"
puts "REPS: #{REPS}"
puts "WARMUPS: #{WARMUPS}"
puts

puts '-'*66
benchmark 'Writing (warmup)', :test_writing, WARMUPS
benchmark 'Writing', :test_writing
puts '-'*66
puts
benchmark 'Reading (warmup)', :test_reading, WARMUPS
benchmark 'Reading', :test_reading
puts '-'*66
puts
puts
