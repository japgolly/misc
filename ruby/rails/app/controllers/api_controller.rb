class ApiController < ApplicationController

  def json
#    puts '='*60
#    puts "Params: #{params.inspect}\n\n"
#    puts "Body: #{request.body.read}\n\n"
#    puts "Params map: #{params['map'].inspect}\n\n"
#    puts "Req headers: #{request.headers.keys.sort.inspect}\n\n"
#    puts "Content type: #{request.headers['CONTENT_TYPE']}\n\n"

    map= params[:map]

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

    render json: MultiJson.encode(results)
  end
end

