// vim:et sw=2 ts=2:
package main

import (
  "encoding/json"
  "fmt"
  "io/ioutil"
  "log"
  "net/http"
  "sort"
)

func main() {
  http.Handle("/api.json", http.HandlerFunc(apiHandler))
  fmt.Println("Starting server...")
  log.Fatal(http.ListenAndServe(":8338", nil))
}


func apiHandler(writer http.ResponseWriter, req *http.Request) {
  var err error

  body, err := ioutil.ReadAll(req.Body)
  if err == nil {
    result,err := api(string(body))
    if err == nil {
      writer.Write([]byte(result))
      writer.Header().Set("Content-Type", "text/json")
      writer.Header().Set("Content-Length", string(len(result)))
      return
    }
  }

  fmt.Println("Failure:",err)
  log.Fatal(err)
  http.Error(writer, err.Error(), 500)
}


func api(request string) (string,error) {
  //fmt.Println(request)
  const nilStr = ""

  // Decode JSON
  var j map[string]map[string]int
  err := json.Unmarshal([]byte(request), &j)
  if err != nil { return nilStr,err }
  //fmt.Println("Unmarshalled:", j)

  // Process
  m := j["map"]
  var keys []string = make([]string, len(m))
  var values []int = make([]int, len(m))
  i := 0
  for k, v := range j["map"] {
    keys[i] = k
    values[i] = v
    i++
  }
  sort.Strings(keys)
  sort.Ints(values)
  results := map[string]interface{}{
    "keys":   keys,
    "values": values,
  }

  // Encode JSON
  result, err := json.Marshal(results)
  if err != nil { return nilStr,err }
  //fmt.Println("Result:", string(result))
  return string(result),nil
}
