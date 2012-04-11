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

    results= {
      keys: keys=[],
      values: values=[],
    }
    map.each do |k,v|
      keys<< k
      values<< v
    end
    keys.sort!
    values.sort!

    #puts "\n#{results.inspect}\n\n"
    #puts "\n#{results.to_json.inspect}\n\n"
    #puts "\n#{MultiJson.encode(results).inspect}\n\n"
    MultiJson.encode(results)
  end
end
