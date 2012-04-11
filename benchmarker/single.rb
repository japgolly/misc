#!/usr/bin/env ruby
require "rubygems"
require "bundler/setup"
Bundler.require :default

unless ARGV.length == 1
  puts "Usage: #{File.basename __FILE__} <script.yaml>"
  exit 1
end

cfg= YAML.load_file(ARGV[0])
req= cfg['request']
exp= cfg['expected_response'] || {}

request_options= {body: req['body'], headers: req['headers'], format: :plain}.freeze
response= HTTParty.post(req['uri'], request_options)

if exp['body'] and response.body != exp['body']
  puts "UNEXPECTED RESPONSE."
  puts "\n#{response.body}\n\n#{response.body.inspect}"
  exit 2
end

puts "Pass."

