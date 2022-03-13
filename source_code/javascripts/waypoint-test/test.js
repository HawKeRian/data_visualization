const startLat =[];
const startLng =[];
const endlat =[];
const endlng =[];
const routeID =[];
const hour =[];
const getWP = new Object;
var delay = 0;
const output = "output.text"
var delayFactor = 0;

function ReadFile(file) {
  return file.text()
}

function removeExtraSpace(stringData) {
  stringData = stringData.replace(/,( *)/gm, ",")  // remove extra space
  stringData = stringData.replace(/^ *| *$/gm, "") // remove space on the beginning and end.
  return stringData  
}

window.onload = () => {
  const inputFile = document.getElementById("uploadFile")
  inputFile.onchange = () => {
    const inputValue = inputFile.value
    console.log(inputValue)
    if (inputValue === "") {
      return
    }

    const selectedFile = document.getElementById('uploadFile').files[0]
    console.log(selectedFile)
    const promise = new Promise(resolve => {
      const fileContent = ReadFile(selectedFile)
      resolve(fileContent)
    })

    promise.then(fileContent => {
      // Use promise to wait for the file reading to finish.
      fileContent = removeExtraSpace(fileContent)
      const myObj = $.csv.toObjects(fileContent)

      for(i=0; i<myObj.length; i++){
        startLat[i] = myObj[i].starty
        startLng[i] = myObj[i].startx
        endlat[i] = myObj[i].endy
        endlng[i] = myObj[i].endx
        routeID[i] = myObj[i].ID
        hour[i] = myObj[i].start_hour
      }
      // console.log(routeID,startLat, startLng, endlat, endlng);
      // console.log(routeID)
    })
  }
}

$(document).ready(function(){
  $('#show-map').on('click',initMap)
});

function initMap() {
  // var i =0;
  var pointA = [];
  var pointB = [];
  //pointCenter = new google.maps.LatLng({lat: startLat[0].toFixed(1),lng: startLng[0].toFixed(1)});
  const markerArray = [];
  // Create a map and center it on pointCenter.
  startCenLat = parseFloat(startLat[0]).toFixed(1);
  startCenLng = parseFloat(startLng[0]).toFixed(1);

  $('#show-map').click(function () {
    var mapOptions = {
      center: {lat: startCenLat ,lng: startCenLng},
      //center: {lat: startLat[0],lng: startLng[0]},
      zoom: 8
    };
    var map = new google.maps.Map(document.getElementById('map'), mapOptions);
    // Instantiate an info window to hold step text.
    const stepDisplay = new google.maps.InfoWindow();
    // Display the route between the initial start and end selections.
    for(i=0; i<startLat.length; i++){
      pointA[i] = new google.maps.LatLng(startLat[i], startLng[i]),
      pointB[i] = new google.maps.LatLng(endlat[i], endlng[i]);
    }

    for(i=0; i<startLat.length; i++){
      // Instantiate a directions service.
      const directionsService = new google.maps.DirectionsService();
      // Create a renderer for directions and bind it to the map.
      const directionsRenderer = new google.maps.DirectionsRenderer({ map: map });

      calculateAndDisplayRoute(
        directionsRenderer,
        directionsService,
        markerArray,
        stepDisplay,
        map,
        pointA[i],
        pointB[i],
        routeID[i]
      );
    }
    // getWayPoints()
  });
  
}
google.maps.event.addDomListener(window, 'onload', initMap);

function m_get_directions_route (request,directionsRenderer,directionsService,markerArray,stepDisplay,map,route) {
      directionsService.route(request, function(result, status) {
        if (status === google.maps.DirectionsStatus.OK) {
            directionsRenderer.setDirections(result);
            showSteps(result, markerArray, stepDisplay, map,route);
        } else if (status === google.maps.DirectionsStatus.OVER_QUERY_LIMIT) {
            delayFactor++;
            setTimeout(function () {
              m_get_directions_route(request,directionsRenderer,directionsService,markerArray,stepDisplay,map,route);
            }, delayFactor * 1000);
        } else {
            console.log("Route: " + status);
        }
    });
} 



function calculateAndDisplayRoute(
  directionsRenderer,
  directionsService,
  markerArray,
  stepDisplay,
  map,
  pointA,
  pointB,
  route
) {
  // First, remove any existing markers from the map.
  for (let i = 0; i < markerArray.length; i++) {
    markerArray[i].setMap(null);
  }
  // Retrieve the start and end locations and create a DirectionsRequest using
  // Scooter directions.
  var request = {
                  origin: pointA,
                  destination: pointB,
                  travelMode: google.maps.TravelMode.BICYCLING,
                }
  m_get_directions_route(request,directionsRenderer,directionsService,markerArray,stepDisplay,map,route);
  // directionsService
  //         .route(request)
  //         .then((result) => {
  //           // Route the directions and pass the response to a function to create
  //           // markers for each step.
  //           // document.getElementById("warnings-panel").innerHTML =
  //           //   "<b>" + result.routes[0].warnings + "</b>";
            
  //           directionsRenderer.setDirections(result);
  //           showSteps(result, markerArray, stepDisplay, map,route);
  //         })
  //         .catch((e) => {
  //           window.alert("Directions request failed due to " + e);
  // });
}

function showSteps(directionResult, markerArray, stepDisplay, map, route) {
  // For each step, place a marker, and add the text to the marker's infowindow.
  // Also attach the marker to an array so we can keep track of it and remove it
  // when calculating new routes.
  const myRoute = directionResult.routes[0].legs[0];
  const latPos = [];
  const lngPos = [];
  const waypoint = [];

  for (let i = 0; i < myRoute.steps.length; i++) {
    const marker = (markerArray[i] =
      markerArray[i] || new google.maps.Marker());
    marker.setMap(map);
    marker.setPosition(myRoute.steps[i].start_location);
    attachInstructionText(
      stepDisplay,
      marker,
      myRoute.steps[i].instructions,
      map
    );
  }
  //Print marker's position

  for(let i=0; i<myRoute.steps.length; i++){
      latPos[i] = markerArray[i].position.lat();
      lngPos[i] = markerArray[i].position.lng();
      waypoint[i] = {'Latitude': latPos[i],'Longitude': lngPos[i]}
      if(i==myRoute.steps.length-1){
        waypoint[i+1] = {'Latitude': parseFloat(endlat[i]),'Longitude': parseFloat(endlng[i])}
      }
  }


  //Show getWP value
  for(i=0; i<routeID.length; i++){
    if(route == routeID[i]){
      getWP[i] = waypoint
      break;
    }
  }
  
}

function attachInstructionText(stepDisplay, marker, text, map) {
  google.maps.event.addListener(marker, "click", () => {
    // Open an info window when the marker is clicked on, containing the text
    // of the step.
    stepDisplay.setContent(text);
    stepDisplay.open(map, marker);
  });
}

function WayPoints(content, fileName, contentType){
  
  var a = document.createElement("a");
  var file = new Blob([content], {type: contentType});
  a.href = URL.createObjectURL(file);
  a.download = fileName;
  a.click();
}

function getWayPoints(){
  const myJSON = []
  for(i=0; i<routeID.length;i++){
    // myJSON[i] = JSON.stringify(getWP[i],null,4);
    myJSON[i] = JSON.stringify(getWP[i]);
    console.log(getWP[i])
    console.log(myJSON[i])
  }
  WayPoints(myJSON, 'output.txt', 'text/plain');
}

function getStartHour(){
  const myJSON2 = []
  for(i=0; i<routeID.length;i++){
    // myJSON[i] = JSON.stringify(getWP[i],null,4);
    myJSON2[i] = JSON.stringify(hour[i]);
  }
  WayPoints(myJSON2, 'timeOutput.txt', 'text/plain');
}

function getLatLng(){
  const myJSON3 = []
  const opPoint = []
  for(i=0; i<routeID.length;i++){
    opPoint[i] = [{'Latitude': startLat[i],'Longitude': startLng[i]},{'Latitude': endlat[i],'Longitude': endlng[i]}]
    finalOP = opPoint
  }
  // console.log(finalOP);


  for(i=0; i<routeID.length;i++){
    myJSON3[i] = JSON.stringify(finalOP[i]);
    // console.log("S_lat",startLat[i],"S_lng", startLng[i])
    // console.log("E_lat",endlat[i],"E_lng",endlng[i])
  }
  WayPoints(myJSON3, 'timeOutput.txt', 'text/plain');
}

// function getWayPoints(){
//   console.log('getWP',getWP)
//   const myJSON = JSON.parse(JSON.stringify(getWP[i]));
//   console.log('output',myJSON)
// }