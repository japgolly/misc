package golly;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

@WebServlet("/api.json")
public class ApiJson extends HttpServlet {

	private static final Gson gson = new Gson();

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// Read input
		BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
		Map<?,?> map = gson.fromJson(reader, Map.class);
		map = (Map<?,?>) map.get("map");

		// Reverse for output
		Map<Object,Object> results = new HashMap<Object,Object>();
		for (Map.Entry<?,?> e : map.entrySet())
			results.put(e.getValue(), e.getKey());
		final String resultJson = gson.toJson(results);

		response.getWriter().write(resultJson);
	}
}
