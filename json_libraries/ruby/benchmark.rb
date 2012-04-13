#!/usr/bin/env ruby
require 'rubygems'
require 'bundler'
Bundler.require :default
require 'benchmark'

#-------------------------------------------------------------------------------

ENGINES= ARGV.empty? ? MultiJson::REQUIREMENT_MAP.map{|e| e[1]} : ARGV.map(&:to_sym)
REPS= 100000
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

def test_encode(engine)
  MultiJson.engine= engine
  for i in 1..REPS; MultiJson.encode DATA end
end

def test_decode(engine)
  MultiJson.engine= engine
  data= MultiJson.encode(DATA).freeze
  for i in 1..REPS; MultiJson.decode data end
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

benchmark 'Encoding (warmup)', :test_encode, WARMUPS
benchmark 'Encoding', :test_encode
benchmark 'Decoding (warmup)', :test_decode, WARMUPS
benchmark 'Decoding', :test_decode
