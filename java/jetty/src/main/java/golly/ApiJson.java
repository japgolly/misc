package golly;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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

		// Process input, create results
		final List<String> keys = new ArrayList<String>();
		final List<Integer> values = new ArrayList<Integer>();
		for (Map.Entry<?,?> e : map.entrySet()) {
			keys.add((String) e.getKey());
			values.add(((Double) e.getValue()).intValue());
		}
		Collections.sort(keys);
		Collections.sort(values);
		final Map results = new TreeMap();
		results.put("keys", keys);
		results.put("values", values);

		// Write result
		final String resultJson = gson.toJson(results);
		response.getWriter().write(resultJson);
	}
}
