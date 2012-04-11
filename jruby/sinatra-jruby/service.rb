#!/usr/bin/env ruby
require "rubygems"
require "bundler/setup"
Bundler.require :default

class MyApp < Sinatra::Base
  disable :protection

  post '/api.json' do
    #content_type :json

    #map= params[:map]
    map= MultiJson.decode(request.body.read)['map']
    #puts "\n#{map}\n\n"

    results= {}
    map.each do |k,v|
      results[v]= k
    end

    #puts "\n#{results.inspect}\n\n"
    #puts "\n#{results.to_json.inspect}\n\n"
    #puts "\n#{MultiJson.encode(results).inspect}\n\n"
    MultiJson.encode(results)
  end
end
