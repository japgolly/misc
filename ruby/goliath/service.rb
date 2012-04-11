#!/usr/bin/env ruby
require "rubygems"
require "bundler/setup"
Bundler.require :default

class MyServiceBro < Goliath::API
  #use Goliath::Rack::Tracer             # log trace statistics
  #use Goliath::Rack::DefaultMimeType    # cleanup accepted media types
  #use Goliath::Rack::Render, 'json'     # auto-negotiate response format
  #use Goliath::Rack::Params             # parse & merge query and body parameters

  def process(map)
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

    results
  end

  def response_with_params_on(env)
    map= env.params['map']

    results= process(map)

    [200, {}, results]
  end


  def response(env)
    input= env['rack.input'].read
    map= MultiJson.decode(input)['map']

    results= process(map)

    [200,{}, MultiJson.encode(results)]
  end
end
