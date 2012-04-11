class ApiController < ApplicationController

  def json
#    puts '='*60
#    puts "Params: #{params.inspect}\n\n"
#    puts "Body: #{request.body.read}\n\n"
#    puts "Params map: #{params['map'].inspect}\n\n"
#    puts "Req headers: #{request.headers.keys.sort.inspect}\n\n"
#    puts "Content type: #{request.headers['CONTENT_TYPE']}\n\n"

    results= {}
    map= params[:map]
    map.each do |k,v|
      results[v]= k
    end
    render json: MultiJson.encode(results)
  end
end

