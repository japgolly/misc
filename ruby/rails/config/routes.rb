RailsApp::Application.routes.draw do

  controller :api do
    match '/api.json' => :json
  end

end
