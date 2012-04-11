#!/usr/bin/env ruby
require "rubygems"
require "bundler/setup"
Bundler.require :default
require 'active_support/inflector'
require_relative 'runner'

unless ARGV.length == 1
  puts "Usage: #{File.basename __FILE__} <script.yaml>"
  exit 1
end

cfg= YAML.load_file(ARGV[0])
req= cfg['request']
exp= cfg['expected_response'] || {}
warmup_condition= eval cfg['benchmark']['warmup']['stop_condition']
benchmark_condition= eval cfg['benchmark']['real']['stop_condition']

request_options= {body: req['body'], headers: req['headers'], format: :plain}.freeze
hit= lambda do
  response= HTTParty.post(req['uri'], request_options)
#  eb= exp['body']
#  if eb and response.body != eb
#    puts "UNEXPECTED RESPONSE."
#    puts "\nACTUAL:   #{response.body}"
#    puts "\nEXPECTED: #{eb}"
#    exit 2
#  end
end

def pass(name, runner)
  t= Thread.new { runner.run }
  req_per_sec= []
  last_req= 0
  window_size= 3
  until t.join(1)
    current= runner.hits
    req_per_sec<< (this= current - last_req)
    window= req_per_sec[-window_size..-1]
    avg= window ? window.inject{|a,b|a+b} / window.length : nil
    puts "#{name} -- This: #{this}. Total: #{runner.hits}. Avg: #{avg || '?'}."
    last_req= current
  end
  puts "#{name} complete.\n\n"
  runner
end

# Warmup
pass 'Warmup', Runner.new(hit, warmup_condition)

# Benchmark
runner= pass 'Benchmark', Runner.new(hit, benchmark_condition)

puts "Requests: #{runner.hits}"
puts "Time elapsed: %0.2f min" % [runner.duration_min]
puts "Requests/sec: %0.1f" % [runner.hits_per_sec]

