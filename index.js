function add(port, projectId) {

  const root = document.querySelector('#root');
  const view = document.createElement('div');
  view.style.flexGrow = 1;
  view.style.border = "solid 1px #666";
  view.style.padding = "1em";
  view.style.whiteSpace = "pre-wrap";
  const url = `ws://localhost:${port}/project/${projectId}`;
  function log(msg) { view.append(`${msg}\n`); }
  log(`Connecing to ${url}`);
  root.appendChild(view);

  const ws = new WebSocket(url);
  ws.onmessage = (e) => {
    log(e.data);
    // console.log("Received: ", e);
  };
}

add(8081, 1);
add(8082, 1);
add(8081, 2);
add(8082, 2);
