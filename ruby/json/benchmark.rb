#!/usr/bin/env ruby
require 'rubygems'
require 'bundler'
Bundler.require :default
require 'benchmark'

puts "#{RUBY_DESCRIPTION}\n\n"

ENGINES= ARGV.empty? ? MultiJson::REQUIREMENT_MAP.map{|e| e[1]} : ARGV.map(&:to_sym)
REPS= 100000

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

def test_encode(engine)
  MultiJson.engine= engine
  for i in 1..REPS; MultiJson.encode DATA end
end

def test_decode(engine)
  MultiJson.engine= engine
  data= MultiJson.encode(DATA).freeze
  for i in 1..REPS; MultiJson.decode data end
end

puts 'encoding'
Benchmark.bmbm(20) do |x|
  ENGINES.each do |e|
    x.report("#{e}:") { test_encode e }
  end
end
puts

puts 'decoding'
Benchmark.bmbm(20) do |x|
  ENGINES.each do |e|
    x.report("#{e}:") { test_encode e }
  end
end
