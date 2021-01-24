let stompClient = null;

function connect() {
  let socket = new SockJS('/ws-search');
  stompClient = Stomp.over(socket);
  stompClient.connect({}, function (frame) {
    //console.log('Connected: ' + frame);
    stompClient.subscribe('/websocket-broker/search', function (response) {
      //console.log(response);
      let content = JSON.parse(response.body);

      // Delete previous datalist entries
      let datalist = document.getElementById("search-list");
      datalist.innerHTML = '';

      // Collect datalist entries
      let options = '';
      for (let i = 0; i < content.length; i++) {
        //console.log(content[i]);
        options += '<option value="' + content[i].first + '" >'
            + content[i].second + '</option>';
      }

      // Set collected datalist entries
      datalist.innerHTML = options;
    })
  })
}

function disconnect() {
  if (stompClient !== null) {
    stompClient.disconnect();
  }
  //console.log("Disconnected");
}

function sendSearchTerm(search_term) {
  stompClient.send("/ws/search", {}, search_term);
}
